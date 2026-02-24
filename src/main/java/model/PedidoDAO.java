package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.BeanGenerico;
import beans.ClienteBean;
import beans.ItemPedidoBean;
import beans.PedidoBean;
import beans.ProdutoBean;

public class PedidoDAO extends DAO {
	
	/** CONSULTAR PEDIDOS **/
    public ArrayList<PedidoBean> consultarPedidos() {
    	final String sql = "SELECT * FROM pedidos ORDER BY " + PedidoBean.NM_COL_DtPedido + " DESC";
    	ArrayList<PedidoBean> lista = new ArrayList<>();
        try {
        	// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
        	// Executa a query e armazena o resultado 
            ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver pedidos para converter os dados num objeto e coloca-los na lista
            while (rs.next()) {
                lista.add(new PedidoBean(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate()));
            }
            // encerra conexão com o BD
            conexao.close();
        } catch (Exception e) {
        System.out.println(e);
        }
        return lista;
    }
    
    /** INCLUIR PEDIDO E ATUALIZAR ESTOQUE **/
    public void incluirPedido(PedidoBean pPedido, String[] pIdsProdutos, String[] pQuantidades, String[] pDescontos) {
        final String sqlPedido  = "INSERT INTO pedidos (" + PedidoBean.NM_COL_IdCliente + ", " 
                                + PedidoBean.NM_COL_DtPedido + ") VALUES (?,?)";
        final String sqlItens   = "INSERT INTO itens_pedido (" 
				                + ItemPedidoBean.NM_COL_IdPedido + ", " 
				                + ItemPedidoBean.NM_COL_IdProduto + ", " 
				                + ItemPedidoBean.NM_COL_ValorUnitario + ", " 
				                + ItemPedidoBean.NM_COL_Quantidade + ", " 
				                + ItemPedidoBean.NM_COL_Desconto + ") VALUES (?,?,?,?,?)";
        
        final String sqlEstoque = "UPDATE produtos SET " 
                                + ProdutoBean.NM_COL_QuantidadeEstoque + " = " 
                                + ProdutoBean.NM_COL_QuantidadeEstoque + " - ? WHERE " 
                                + ProdutoBean.NM_COL_IdProduto + " = ?";
        try {
        	// Abre conexão com o banco
            Connection conexao = conectar();
            // como aqui acontecem mais de 1 transação de banco, caso ocorra algum problema com a transação, 
            // o restante não será enviado ao BD, parecido com o funcionamento das RNs no iGESP.
            conexao.setAutoCommit(false); 
            
			// Prepara a query para execução no BD, enviando parametro Statement.RETURN_GENERATED_KEYS 
            // que indica que vou querer o retorno das chaves criadas
            PreparedStatement psP = conexao.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psP.setInt(1, pPedido.getIdCliente());
            psP.setDate(2, java.sql.Date.valueOf(pPedido.getDtPedido()));
            // executa sql do pedido
            psP.executeUpdate();
           
            // Pega o ID gerado para o pedido
            ResultSet rs = psP.getGeneratedKeys();
            int idPedidoGerado = 0;
            if (rs.next()) idPedidoGerado = rs.getInt(1);

            //Loop para inserir cada produto e baixar estoque
            for (int i = 0; i < pIdsProdutos.length; i++) {
                int idProd  = Integer.parseInt(pIdsProdutos[i]);
                int qtd     = Integer.parseInt(pQuantidades[i]);
                double desc = Double.parseDouble(pDescontos[i]);

                // Busca o valor atual do produto para gravar no item_pedido (histórico de preço)
                ProdutoBean pb = new ProdutoDAO().selecionarProdutoPorChavePrimaria(idProd);

                // Inserir Item
                PreparedStatement psI = conexao.prepareStatement(sqlItens);
                psI.setInt(1, idPedidoGerado);
                psI.setInt(2, idProd);
                psI.setDouble(3, pb.getValor());
                psI.setInt(4, qtd);
                psI.setDouble(5, desc);
                psI.executeUpdate();

                // Baixar Estoque
                PreparedStatement psE = conexao.prepareStatement(sqlEstoque);
                psE.setInt(1, qtd);
                psE.setInt(2, idProd);
                psE.executeUpdate();
            }

            // Finaliza transação e só agora faço o envio para o BD, garantindo inserções "pela metade"
            conexao.commit(); 
            // encerra conexão com o BD
            conexao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /** EXCLUIR PEDIDO E ATUALIZAR ESTOQUE **/
    public void excluirPedido(int pIdPedido) {
        final String sqlBuscaItens = "SELECT " + ItemPedidoBean.NM_COL_IdProduto + ", " 
                                     + ItemPedidoBean.NM_COL_Quantidade + " FROM itens_pedido WHERE " 
                                     + ItemPedidoBean.NM_COL_IdPedido + " = ?";
        
        final String sqlEstorno = "UPDATE produtos SET " 
                                  + ProdutoBean.NM_COL_QuantidadeEstoque + " = " 
                                  + ProdutoBean.NM_COL_QuantidadeEstoque + " + ? WHERE " 
                                  + ProdutoBean.NM_COL_IdProduto + " = ?";
        
        final String sqlExcluirPedido = "DELETE FROM pedidos WHERE " + PedidoBean.NM_COL_IdPedido + " = ?";

        try {
        	// Abre conexão com o banco
            Connection conexao = conectar();
            // como aqui acontecem mais de 1 transação de banco, caso ocorra algum problema com a transação, 
            // o restante não será enviado ao BD, parecido com o funcionamento das RNs no iGESP.
            conexao.setAutoCommit(false); 
            
            // Recupera os itens do pedido para saber quanto devolver ao estoque
            PreparedStatement psB = conexao.prepareStatement(sqlBuscaItens);
            psB.setInt(1, pIdPedido);
            ResultSet rs = psB.executeQuery();

            while (rs.next()) {
            	// Executa o estorno de cada produto encontrado no pedido
                PreparedStatement psE = conexao.prepareStatement(sqlEstorno);
                psE.setInt(1, rs.getInt(ItemPedidoBean.NM_COL_Quantidade));
                psE.setInt(2, rs.getInt(ItemPedidoBean.NM_COL_IdProduto));
                psE.executeUpdate();
            }
            // Remove o pedido
            PreparedStatement psDel = conexao.prepareStatement(sqlExcluirPedido);
            psDel.setInt(1, pIdPedido);
            psDel.executeUpdate();
            
            // Finaliza transação e só agora faço o envio para o BD
            conexao.commit();
            // encerra conexão com o BD
            conexao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /** CONSULTAR DADOS DO PEDIDO **/
    public BeanGenerico consultarPedidoDetalhado(int pIdPedido) {
        BeanGenerico beanGenericoPedido = null;
        final String sql = "SELECT pedidos.*, clientes." + ClienteBean.NM_COL_Nome + 
                                  " FROM pedidos INNER JOIN clientes ON pedidos." + PedidoBean.NM_COL_IdCliente + 
                                  " = clientes." + ClienteBean.NM_COL_IdCliente + 
                                  " WHERE pedidos." + PedidoBean.NM_COL_IdPedido + " = ?";
        try {
        	// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
            preStmt.setInt(1, pIdPedido);
			// Executa a query e armazena o resultado 
            ResultSet rs = preStmt.executeQuery();
			// Verifica se existem dados para converter num objeto generico e coloca-los no BeanGenerico
            if (rs.next()) {
                beanGenericoPedido = new BeanGenerico();
                beanGenericoPedido.setAtribute(PedidoBean.NM_COL_IdPedido, rs.getInt(PedidoBean.NM_COL_IdPedido));
                beanGenericoPedido.setAtribute(PedidoBean.NM_COL_IdCliente, rs.getInt(PedidoBean.NM_COL_IdCliente));
                beanGenericoPedido.setAtribute(PedidoBean.NM_COL_DtPedido, rs.getDate(PedidoBean.NM_COL_DtPedido).toLocalDate());
                beanGenericoPedido.setAtribute(ClienteBean.NM_COL_Nome, rs.getString(ClienteBean.NM_COL_Nome));
                }
            conexao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return beanGenericoPedido;
    }
    
    /** CONSULTAR ITENS DO PEDIDO E SEUS DADOS **/
    public ArrayList<BeanGenerico> consultarItensPorPedido(int pIdPedido) {
        ArrayList<BeanGenerico> listaItensGenericos = new ArrayList<>();
        final String sql = "SELECT itens_pedido.*, produtos." + ProdutoBean.NM_COL_Descricao + 
                                " FROM itens_pedido INNER JOIN produtos ON itens_pedido." + ItemPedidoBean.NM_COL_IdProduto + 
                                " = produtos." + ProdutoBean.NM_COL_IdProduto + 
                                " WHERE itens_pedido." + ItemPedidoBean.NM_COL_IdPedido + " = ?";
        try {
        	// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
            preStmt.setInt(1, pIdPedido);
			// Executa a query e armazena o resultado 
            ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver dados para converter num objeto generico e coloca-los na lista
            while (rs.next()) {
                BeanGenerico beanGenericoItem = new BeanGenerico();
                beanGenericoItem.setAtribute(ItemPedidoBean.NM_COL_IdItem, rs.getInt(ItemPedidoBean.NM_COL_IdItem));
                beanGenericoItem.setAtribute(ItemPedidoBean.NM_COL_ValorUnitario, rs.getDouble(ItemPedidoBean.NM_COL_ValorUnitario));
                beanGenericoItem.setAtribute(ItemPedidoBean.NM_COL_Quantidade, rs.getInt(ItemPedidoBean.NM_COL_Quantidade));
                beanGenericoItem.setAtribute(ItemPedidoBean.NM_COL_Desconto, rs.getDouble(ItemPedidoBean.NM_COL_Desconto));
                beanGenericoItem.setAtribute(ProdutoBean.NM_COL_Descricao, rs.getString(ProdutoBean.NM_COL_Descricao));
                listaItensGenericos.add(beanGenericoItem);
            }
			// Encerra a conexão com o BD
            conexao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        // retorna a lista
        return listaItensGenericos;
    }
    
    /** CONSULTAR PEDIDOS POR ID **/
	public ArrayList<PedidoBean> consultarPedidosPorId(int pIdPedido) {
		final String sql = "SELECT * FROM pedidos WHERE " + PedidoBean.NM_COL_IdPedido + " = ?";
		ArrayList<PedidoBean> lista = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setInt(1, pIdPedido);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver pedidos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				lista.add(new PedidoBean(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate()));
			}
			// encerra conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}

	/** CONSULTAR PEDIDOS POR PERÍODO DE DATA **/
	public ArrayList<PedidoBean> consultarPedidosPorPeriodo(LocalDate pDataInicio, LocalDate pDataFim) {
		final String sql = "SELECT * FROM pedidos WHERE " + PedidoBean.NM_COL_DtPedido + " BETWEEN ? AND ? ORDER BY " + PedidoBean.NM_COL_DtPedido + " ASC";
		ArrayList<PedidoBean> lista = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setDate(1, java.sql.Date.valueOf(pDataInicio));
			preStmt.setDate(2, java.sql.Date.valueOf(pDataFim));
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver pedidos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				lista.add(new PedidoBean(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate()));
			}
			// encerra conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}
    
    /** CONSULTAR PEDIDOS POR ID DO CLIENTE **/
	public ArrayList<PedidoBean> consultarPedidosPorCliente(int pIdCliente) {
		final String sql = "SELECT * FROM pedidos WHERE " + PedidoBean.NM_COL_IdCliente + " = ? ORDER BY " + PedidoBean.NM_COL_DtPedido + " DESC";
		ArrayList<PedidoBean> lista = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setInt(1, pIdCliente);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver pedidos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				lista.add(new PedidoBean(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate()));
			}
			// encerra conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}

	/** CONSULTAR PEDIDOS POR ID DO PRODUTO **/
	public ArrayList<PedidoBean> consultarPedidosPorProduto(int pIdProduto) {
		final String sql = "SELECT DISTINCT pedidos.* FROM pedidos INNER JOIN itens_pedido itens ON pedidos." + PedidoBean.NM_COL_IdPedido + 
						   " = itens." + ItemPedidoBean.NM_COL_IdPedido + " WHERE itens." + ItemPedidoBean.NM_COL_IdProduto + 
						   " = ? ORDER BY pedidos." + PedidoBean.NM_COL_DtPedido + " DESC";
		ArrayList<PedidoBean> lista = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setInt(1, pIdProduto);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver pedidos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				lista.add(new PedidoBean(rs.getInt(1), rs.getInt(2), rs.getDate(3).toLocalDate()));
			}
			// encerra conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}
}