package processadorRequisicao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.ProdutoBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProdutoDAO;

public class PRProduto {
	/**
	 * */
	private static final long serialVersionUID = 1L;
	public static final String NM_SERVLET_EXIBIR_PRODUTOS = "exibirProdutos";
	public static final String NM_SERVLET_INCLUIR_PRODUTOS = "incluirProduto";
	public static final String NM_SERVLET_ALTERAR_PRODUTOS = "alterarProduto";
	public static final String NM_SERVLET_PROCESSAR_ATUALIZAR_PRODUTO = "processarAlterarProduto";
	public static final String NM_SERVLET_EXCLUIR_PRODUTO = "excluirProduto";
	public static final String NM_SERVLET_CONSULTAR_PRODUTO_PARAMETRO = "consultarProdutoParametro";
	
	public static final String NM_FILTRO_PRODUTO = "filtroProduto";
	public static final String NM_ARRAY_PRODUTO = "listaProdutos";

	public static String NM_JSP_LISTAR  = "/documents/produto/listarProduto.jsp";
	public static String NM_JSP_INCLUIR = "/documents/produto/novoProduto.jsp";
	public static String NM_JSP_ALTERAR = "/documents/produto/alterarProduto.jsp";
	
	ProdutoDAO produtoDAO = new ProdutoDAO();
	// *********************** MÉTODOS RELACIONADO AOS PRODUTOS ***********************
	public void exibirProdutos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Criando o objeto que irá receber os dados do produtos
		ArrayList<ProdutoBean> listaProdutos = produtoDAO.consultarProduto();
		// Encaminhar a lista ao documento listarProdutos.jsp
		request.setAttribute(NM_ARRAY_PRODUTO, listaProdutos);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_LISTAR);
		rd.forward(request, response);
	}
	
	public void incluirProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// cria objeto do produto
		ProdutoBean produto = new ProdutoBean();
		// seta as variáveis
		produto.setDescricao(request.getParameter(ProdutoBean.NM_COL_Descricao));
		produto.setValor(Double.parseDouble(request.getParameter(ProdutoBean.NM_COL_Valor)));
		produto.setQuantidadeEstoque(Integer.parseInt(request.getParameter(ProdutoBean.NM_COL_QuantidadeEstoque)));
		// seta a data do cadastro com a data de hoje
		produto.setDtCadastro(LocalDate.now());
		// chama o DAO para incluir no banco de dados
		produtoDAO.incluirProduto(produto);
		// redirecionar para a pagina listarProdutos.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_PRODUTOS);
	}
	
	public void alterarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recebe o ID do produto e cria objeto setando sua PK
		ProdutoBean produto = new ProdutoBean(Integer.parseInt(request.getParameter(ProdutoBean.NM_COL_IdProduto)));
		// recebe objeto do banco com o DAO
		produto = produtoDAO.selecionarProdutoPorChavePrimaria(produto.getIdProduto());
		// Envia o objeto para o o documento alterarProduto.jsp
		request.setAttribute(ProdutoBean.NM_ENTIDADE, produto);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_ALTERAR);
		rd.forward(request, response);
	}
	
	public void processarAlterarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//obtém variavéis do documento jsp
		int idProduto               = Integer.parseInt(request.getParameter(ProdutoBean.NM_COL_IdProduto));
		String descricaoProduto     = request.getParameter(ProdutoBean.NM_COL_Descricao);
		double valorProduto         = Double.parseDouble(request.getParameter(ProdutoBean.NM_COL_Valor));
		int quantidadeProduto       = Integer.parseInt(request.getParameter(ProdutoBean.NM_COL_QuantidadeEstoque));
		LocalDate dtInclusaoProduto = LocalDate.parse(request.getParameter(ProdutoBean.NM_COL_DtCadastro));
		// Cria Objeto ProdutoBean com os parametros setados
		ProdutoBean produto = new ProdutoBean(idProduto, descricaoProduto, valorProduto, quantidadeProduto, dtInclusaoProduto);
		// chama o DAO para mandar alterar ao BD
		produtoDAO.alterarProduto(produto);
		// redireciona para o documento listarProdutos.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_PRODUTOS);
	}
	
	public void processarExcluirProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recebe o ID do produto e cria objeto setando sua PK
		ProdutoBean produto = new ProdutoBean(Integer.parseInt(request.getParameter(ProdutoBean.NM_COL_IdProduto)));
		// chama o DAO para mandar excluir do BD
		produtoDAO.excluirProduto(produto);
		// redireciona para o documento listarProdutos.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_PRODUTOS);
	}
	
	public void consultarProdutoParametro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ProdutoBean> listaProdutos;
		//obtém filtro do documento jsp
		String filtro = request.getParameter(NM_FILTRO_PRODUTO);
		if (filtro == null || filtro.equals("")) {
			// Se o filtro estiver vazio, traz todos os registro
			listaProdutos = produtoDAO.consultarProduto();
		} else {
			// senão faz consulta passando filtro como parâmetro
			listaProdutos = produtoDAO.consultarProdutoPorDescricaoId(filtro);
		}
		// Envia o filtro para o documento listarProdutos.jsp
		request.setAttribute(NM_ARRAY_PRODUTO, listaProdutos);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_LISTAR);
		rd.forward(request, response);
	}
}