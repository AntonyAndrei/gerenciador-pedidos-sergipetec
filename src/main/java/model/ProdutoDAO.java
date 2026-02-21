package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.ProdutoBean;

public class ProdutoDAO extends DAO {
	/********************************** CRUD PRODUTO ******************************************/
	/** INCLUIR PRODUTO **/
	public void incluirProduto(ProdutoBean pProduto) {
		final String sql = "INSERT INTO PRODUTOS (descricao, valor, quantidadeEstoque, dt_cadastro) VALUES (?,?,?,?)";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pProduto.getDescricao());
			preStmt.setDouble(2, pProduto.getValor());
			preStmt.setInt(3, pProduto.getQuantidadeEstoque());
			preStmt.setDate(4, java.sql.Date.valueOf(pProduto.getDtCadastro()));
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CONSULTAR PRODUTO **/
	public ArrayList<ProdutoBean> consultarProduto() {
		final String sql = "SELECT * FROM PRODUTOS ORDER BY DESCRICAO";
		ArrayList<ProdutoBean> listaProdutos = new ArrayList<>();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Executa a query e armazena o resultado 
			ResultSet rs = preStmt.executeQuery();
			// laço While executado enquanto houver produtos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				int idProduto        = rs.getInt(1);
				String descricao     = rs.getString(2);
				double valor         = rs.getDouble(3);
				int quantidade       = rs.getInt(4);
				LocalDate dtCadastro = rs.getDate(5).toLocalDate();
				// Preenche o objeto 
				ProdutoBean produtoAtual = new ProdutoBean(idProduto, descricao, valor, quantidade, dtCadastro);
				// Popula a lista
				listaProdutos.add(produtoAtual);
			}
			// Encerra a conexão com o BD
			conexao.close();
			// retorna a lista
			return listaProdutos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** SELECIONAR PRODUTO **/
	public ProdutoBean selecionarProdutoPorChavePrimaria(int pIdProduto) {
		final String sql = "SELECT * FROM PRODUTOS WHERE " + ProdutoBean.NM_COL_IdProduto + " = ? ";
		ProdutoBean produto = new ProdutoBean();
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setInt(1, pIdProduto);
			// Executa a query e armazena o resultado  no objeto ProdutoBean
			ResultSet rs = preStmt.executeQuery();
			// Verifica se existem dados do produto para converter os dados num objeto e coloca-los no ProdutoBean
			if (rs.next()) {
				produto.setIdProduto(rs.getInt(1));
				produto.setDescricao(rs.getString(2));
				produto.setValor(rs.getDouble(3));
				produto.setQuantidadeEstoque(rs.getInt(4));
				produto.setDtCadastro(rs.getDate(5).toLocalDate());
			}
			// Encerra a conexão com o BD
			conexao.close();
			return produto;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** ALTERAR PRODUTO **/
	public void alterarProduto(ProdutoBean pProduto) {
		String sql = "UPDATE PRODUTOS SET " + ProdutoBean.NM_COL_Descricao + " = ?, "
				+ ProdutoBean.NM_COL_Valor + " = ?,"
				+ ProdutoBean.NM_COL_QuantidadeEstoque + " = ?,"
				+ ProdutoBean.NM_COL_DtCadastro + " = ? "
				+ "WHERE " + ProdutoBean.NM_COL_IdProduto + " = ?";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setString(1, pProduto.getDescricao());
			preStmt.setDouble(2, pProduto.getValor());
			preStmt.setInt(3, pProduto.getQuantidadeEstoque());
			preStmt.setDate(4, java.sql.Date.valueOf(pProduto.getDtCadastro()));
			preStmt.setInt(5, pProduto.getIdProduto());
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** EXCLUIR PRODUTO **/
	public void excluirProduto(ProdutoBean pProduto) {
		String sql = "DELETE FROM PRODUTOS WHERE " + ProdutoBean.NM_COL_IdProduto + " = ?";
		try {
			// Abre conexão com o banco
			Connection conexao = conectar();
			// Prepara a query para execução no BD
			PreparedStatement preStmt = conexao.prepareStatement(sql);
			// Substitui parâmetros "?"
			preStmt.setInt(1, pProduto.getIdProduto());
			// Executa query
			preStmt.executeUpdate();
			// Encerra a conexão com o BD
			conexao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CONSULTAR PRODUTO POR DESCRICAO OU ID **/
	public ArrayList<ProdutoBean> consultarProdutoPorDescricaoId(String pFiltro) {
		// Busca por ID ou descricao que contenha o texto
		final String sql = "SELECT * FROM PRODUTOS WHERE " + ProdutoBean.NM_COL_IdProduto + " = ? " 
					+ " OR " + ProdutoBean.NM_COL_Descricao + " LIKE ? " 
					+ " ORDER BY " + ProdutoBean.NM_COL_Descricao;
		ArrayList<ProdutoBean> listaProdutos = new ArrayList<>();
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
			// laço While executado enquanto houver produtos para converter os dados num objeto e coloca-los na lista
			while (rs.next()) {
				int idProduto        = rs.getInt(1);
				String descricao     = rs.getString(2);
				double valor         = rs.getDouble(3);
				int quantidade       = rs.getInt(4);
				LocalDate dtCadastro = rs.getDate(5).toLocalDate();
				
				ProdutoBean produtoAtual = new ProdutoBean(idProduto, descricao, valor, quantidade, dtCadastro);
				// Popula a lista
				listaProdutos.add(produtoAtual);
			}
			// Encerra a conexão com o BD
			conexao.close();
			return listaProdutos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}