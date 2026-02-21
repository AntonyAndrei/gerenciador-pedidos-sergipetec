package processadorRequisicao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.ClienteBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ClienteDAO;

public class PRCliente {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NM_SERVLET_EXIBIR_CLIENTES = "exibirClientes";
	public static final String NM_SERVLET_INCLUIR_CLIENTES = "incluirCliente";
	public static final String NM_SERVLET_ALTERAR_CLIENTES = "alterarCliente";
	public static final String NM_SERVLET_PROCESSAR_ATUALIZAR_CLIENTE = "processarAlterarCliente";
	public static final String NM_SERVLET_EXCLUIR_CLIENTE = "excluirCliente";
	public static final String NM_SERVLET_CONSULTAR_CLIENTE_PARAMETRO = "consultarClienteParametro";
	
	public static final String NM_FILTRO_CLIENTE = "filtroCliente";
	public static final String NM_ARRAY_CLIENTE = "listaClientes";

	public static String NM_JSP_LISTAR  = "/documents/cliente/listarClientes.jsp";
	public static String NM_JSP_INCLUIR = "/documents/cliente/novoCliente.jsp";
	public static String NM_JSP_ALTERAR = "/documents/cliente/alterarCliente.jsp";
	
	ClienteDAO clienteDAO = new ClienteDAO();
	// *********************** MÉTODOS RELACIONADO AOS CLIENTES ***********************
	public void exibirClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Criando o objeto que irá receber os dados do clientes
		ArrayList<ClienteBean> listaClientes = clienteDAO.consultarCliente();
		// Encaminhar a lista ao documento listarClientes.jsp
		request.setAttribute(NM_ARRAY_CLIENTE, listaClientes);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_LISTAR);
		rd.forward(request, response);
	}
	
	public void incluirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// cria objeto do cliente
		ClienteBean cliente = new ClienteBean();
		// seta as variáveis
		cliente.setNome(request.getParameter(ClienteBean.NM_COL_Nome));
		cliente.setEmail(request.getParameter(ClienteBean.NM_COL_Email));
		// seta a data do cadastro com a data de hoje
		cliente.setDtCadastro(LocalDate.now());
		// chama o DAO para incluir no banco de dados
		clienteDAO.incluirCliente(cliente);
		// redirecionar para a pagina listarClientes.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_CLIENTES);
	}
	
	public void alterarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recebe o ID do cliente e cria objeto setando sua PK
		ClienteBean cliente = new ClienteBean(request.getParameter(ClienteBean.NM_COL_IdCliente));
		// recebe objeto do banco com o DAO
		cliente = clienteDAO.selecionarClientePorChavePrimaria(cliente.getIdCliente());
		// Envia o objeto para o o documento alterarCliente.jsp
		request.setAttribute(ClienteBean.NM_ENTIDADE, cliente);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_ALTERAR);
		rd.forward(request, response);
	}
	
	public void processarAlterarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//obtém variavéis do documento jsp
		String idCliente            = request.getParameter(ClienteBean.NM_COL_IdCliente);
		String nomeCliente          = request.getParameter(ClienteBean.NM_COL_Nome);
		String emailCliente         = request.getParameter(ClienteBean.NM_COL_Email);
		LocalDate dtInclusaoCliente = LocalDate.parse(request.getParameter(ClienteBean.NM_COL_DtCadastro));
		// Cria Objeto ClienteBean com os parametros setados
		ClienteBean cliente = new ClienteBean(idCliente, nomeCliente, emailCliente, dtInclusaoCliente);
		// chama o DAO para mandar alterar ao BD
		clienteDAO.alterarCliente(cliente);
		// redireciona para o documento gerenciadorPedidos.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_CLIENTES);
	}
	
	public void processarExcluirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recebe o ID do cliente e cria objeto setando sua PK
		ClienteBean cliente = new ClienteBean(request.getParameter(ClienteBean.NM_COL_IdCliente));
		// chama o DAO para mandar excluir do BD
		clienteDAO.excluirCliente(cliente);
		// redireciona para o documento gerenciadorPedidos.jsp
		response.sendRedirect(NM_SERVLET_EXIBIR_CLIENTES);
	}
	
	// Adicione este método ao final da classe
	public void consultarClienteParametro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ClienteBean> listaClientes;
		//obtém filtro do documento jsp
		String filtro = request.getParameter(NM_FILTRO_CLIENTE);
		if (filtro == null || filtro.equals("")) {
			// Se o filtro estiver vazio, traz todos os registro
			listaClientes = clienteDAO.consultarCliente();
		} else {
			// senão faz consulta passando filtro como parâmetro
			listaClientes = clienteDAO.consultarClientePorNomeId(filtro);
		}
		// Envia o filtro para o documento alterarCliente.jsp
		request.setAttribute(NM_ARRAY_CLIENTE, listaClientes);
		RequestDispatcher rd = request.getRequestDispatcher(NM_JSP_LISTAR);
		rd.forward(request, response);
	}
}
