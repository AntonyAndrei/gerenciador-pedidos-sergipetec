package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
        final String sqlItens   = "INSERT INTO itens_pedido (idPedido, idProduto, valorUnitario, quantidade, desconto) VALUES (?,?,?,?,?)";
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
}