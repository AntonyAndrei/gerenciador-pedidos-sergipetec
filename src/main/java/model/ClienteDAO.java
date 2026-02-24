package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.BeanGenerico;
import beans.ClienteBean;
import beans.ItemPedidoBean;
import beans.PedidoBean;

public class ClienteDAO extends DAO {
	/********************************** CRUD CLIENTE ******************************************/
	/** INCLUIR CLINTE **/
	public void incluirCliente(ClienteBean pCliente) {
		final String sql = "insert into clientes (nome, email, dt_cadastro) values (?,?,?)";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pCliente.getNome());
			preStmt.setString(2, pCliente.getEmail());
			preStmt.setDate(3, java.sql.Date.valueOf(pCliente.getDtCadastro()));
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CONSULTAR CLINTE **/
	public ArrayList<ClienteBean> consultarCliente() {
		final String sql = "SELECT * FROM CLIENTES ORDER BY NOME";
		ArrayList<ClienteBean> listaClientes = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver clientes para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				String idCliente     = rs.getString(1);
				String nome          = rs.getString(2);
				String email         = rs.getString(3);
				LocalDate dtCadastro = rs.getDate(4).toLocalDate();
				// Preenche o objeto 
				ClienteBean clienteAtual = new ClienteBean(idCliente, nome, email, dtCadastro);
				// Popula a lista
				listaClientes.add(clienteAtual);
			}
			// Encerra a conexão com o BD
			conexao.close();
			// retorna a lista
			return listaClientes;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** SELECIONAR CLIENTE **/
	public ClienteBean selecionarClientePorChavePrimaria(String pIdCliente) {
		final String sql = "SELECT * FROM CLIENTES WHERE " + ClienteBean.NM_COL_IdCliente + " = ? ";
		ClienteBean cliente = new ClienteBean();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pIdCliente);
			// Executa a query e armazena o resultado  no objeto ClienteEean
			ResultSet rs = preStmt.executeQuery();
			// Verifica se existem dados do cliente para converter os dados num objeto e coloca-los no Clientebean
			if (rs.next()) {
				cliente.setIdCliente(rs.getString(1));
				cliente.setNome(rs.getString(2));
				cliente.setEmail(rs.getString(3));
				cliente.setDtCadastro(rs.getDate(4).toLocalDate());
			}
			// Encerra a conexão com o BD
			conexao.close();
			return cliente;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** ALTERAR CLIENTE **/
	public void alterarCliente(ClienteBean pCliente) {
		String sql = "UPDATE CLIENTES SET " + ClienteBean.NM_COL_Nome + " = ?, "
				+ ClienteBean.NM_COL_Email + " = ?,"
				+ ClienteBean.NM_COL_DtCadastro + " = ? "
				+ "WHERE " + ClienteBean.NM_COL_IdCliente + " = ?";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pCliente.getNome());
			preStmt.setString(2, pCliente.getEmail());
			preStmt.setDate(3, java.sql.Date.valueOf(pCliente.getDtCadastro()));
			preStmt.setString(4, pCliente.getIdCliente());
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** EXCLUIR CLIENTE **/
	public void excluirCliente(ClienteBean pCliente) {
		String sql = "DELETE FROM CLIENTES WHERE " + ClienteBean.NM_COL_IdCliente + " = ?";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pCliente.getIdCliente());
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CONSULTAR CLIENTE POR NOME OU ID **/
	public ArrayList<ClienteBean> consultarClientePorNomeId(String pFiltro) {
		// Busca por ID ou nome que contenha o texto
		final String sql = "SELECT * FROM CLIENTES WHERE " + ClienteBean.NM_COL_IdCliente + " = ? " 
					+ " OR " + ClienteBean.NM_COL_Nome + " LIKE ? " 
					+ " ORDER BY " + ClienteBean.NM_COL_Nome;
		ArrayList<ClienteBean> listaClientes = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pFiltro);
			preStmt.setString(2, "%" + pFiltro + "%");
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver clientes para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				String idCliente     = rs.getString(1);
				String nome          = rs.getString(2);
				String email         = rs.getString(3);
				LocalDate dtCadastro = rs.getDate(4).toLocalDate();
				
				ClienteBean clienteAtual = new ClienteBean(idCliente, nome, email, dtCadastro);
				listaClientes.add(clienteAtual);
			}
			// Encerra a conexão com o BD
			conexao.close();
			return listaClientes;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** CONSULTAR CLIENTES COM JOIN **/
	public ArrayList<BeanGenerico> consultarClientesGenerico() {
		final String sql = "SELECT clientes.*, " + " (SELECT COUNT(*) FROM pedidos WHERE pedidos."
				+ PedidoBean.NM_COL_IdCliente + " = clientes." + ClienteBean.NM_COL_IdCliente + ") as qtd_pedidos, "
				+ " (SELECT SUM((itens_pedido." + ItemPedidoBean.NM_COL_ValorUnitario + " * itens_pedido."
				+ ItemPedidoBean.NM_COL_Quantidade + ") - itens_pedido." + ItemPedidoBean.NM_COL_Desconto + ") "
				+ "  FROM itens_pedido INNER JOIN pedidos ON itens_pedido." + ItemPedidoBean.NM_COL_IdPedido
				+ " = pedidos." + PedidoBean.NM_COL_IdPedido + " " + "  WHERE pedidos." + PedidoBean.NM_COL_IdCliente
				+ " = clientes." + ClienteBean.NM_COL_IdCliente + ") as valor_total "
				+ " FROM clientes ORDER BY clientes." + ClienteBean.NM_COL_Nome;
		
		ArrayList<BeanGenerico> lista = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While para converter os dados no BeanGenerico
			while (rs.next()) {
				BeanGenerico clienteGenerico = new BeanGenerico();
				clienteGenerico.setAtribute(ClienteBean.NM_COL_IdCliente, rs.getString(1));
				clienteGenerico.setAtribute(ClienteBean.NM_COL_Nome, rs.getString(2));
				clienteGenerico.setAtribute(ClienteBean.NM_COL_Email, rs.getString(3));
				clienteGenerico.setAtribute(ClienteBean.NM_COL_DtCadastro, rs.getDate(4).toLocalDate());
				clienteGenerico.setAtribute(ClienteBean.NM_COL_Generica_Qnt_Pedidos, rs.getInt(5));
				clienteGenerico.setAtribute(ClienteBean.NM_COL_Generica_Valor_total, rs.getDouble(6));
				lista.add(clienteGenerico);
			}
			// encerra conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return lista;
	}
}
