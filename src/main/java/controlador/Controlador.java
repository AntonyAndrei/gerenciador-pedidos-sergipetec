package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import processadorRequisicao.PRCliente;
import processadorRequisicao.PRProduto;
import processadorRequisicao.PRPedido;

@WebServlet(urlPatterns = {
		"/Controlador",
		// **************** SERVLETS CLIENTES ***********************/
		"/" + PRCliente.NM_SERVLET_EXIBIR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_INCLUIR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_ALTERAR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_PROCESSAR_ATUALIZAR_CLIENTE,
		"/" + PRCliente.NM_SERVLET_EXCLUIR_CLIENTE,
		"/" + PRCliente.NM_SERVLET_CONSULTAR_CLIENTE_PARAMETRO,
		// **************** SERVLETS PRODUTOS ***********************/
		"/" + PRProduto.NM_SERVLET_EXIBIR_PRODUTOS,
		"/" + PRProduto.NM_SERVLET_INCLUIR_PRODUTOS,
		"/" + PRProduto.NM_SERVLET_ALTERAR_PRODUTOS,
		"/" + PRProduto.NM_SERVLET_PROCESSAR_ATUALIZAR_PRODUTO,
		"/" + PRProduto.NM_SERVLET_EXCLUIR_PRODUTO,
		"/" + PRProduto.NM_SERVLET_CONSULTAR_PRODUTO_PARAMETRO,
		// **************** SERVLETS PEDIDOS ***********************/
		"/" + PRPedido.NM_SERVLET_EXIBIR_PEDIDOS,
		"/" + PRPedido.NM_SERVLET_INCLUIR_PEDIDO,
	    "/" + PRPedido.NM_SERVLET_EXIBIR_INCLUIR,
	    "/" + PRPedido.NM_SERVLET_EXCLUIR_PEDIDO,
	    "/" + PRPedido.NM_SERVLET_DETALHAR_PEDIDO,
	    "/" + PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO
		})
public class Controlador extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
       
    public Controlador() {
        super();
    }
    
    PRCliente aPRCliente = new PRCliente();
    PRProduto aPRProduto = new PRProduto();
    PRPedido aPRPedido   = new PRPedido();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processarRequisicao(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processarRequisicao(request, response);
    }

	protected void processarRequisicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		// **************** SERVLETS CLIENTES ***********************/
		if (action.equals("/" + PRCliente.NM_SERVLET_EXIBIR_CLIENTES)) {
			aPRCliente.exibirClientes(request, response);
		}
		
		if (action.equals("/" + PRCliente.NM_SERVLET_INCLUIR_CLIENTES)) {
			aPRCliente.incluirCliente(request, response);
		}
		
		if (action.equals("/" + PRCliente.NM_SERVLET_ALTERAR_CLIENTES)) {
			aPRCliente.alterarCliente(request, response);
		}
		
		if (action.equals("/" + PRCliente.NM_SERVLET_PROCESSAR_ATUALIZAR_CLIENTE)) {
			aPRCliente.processarAlterarCliente(request, response);
		}
		
		if (action.equals("/" + PRCliente.NM_SERVLET_EXCLUIR_CLIENTE)) {
			aPRCliente.processarExcluirCliente(request, response);
		}
		
		if (action.equals("/" + PRCliente.NM_SERVLET_CONSULTAR_CLIENTE_PARAMETRO)) {
		    aPRCliente.consultarClienteParametro(request, response);
		}
		
		// **************** SERVLETS PRODUTOS ***********************/
		if (action.equals("/" + PRProduto.NM_SERVLET_EXIBIR_PRODUTOS)) {
			aPRProduto.exibirProdutos(request, response);
		}
		
		if (action.equals("/" + PRProduto.NM_SERVLET_INCLUIR_PRODUTOS)) {
			aPRProduto.incluirProduto(request, response);
		}
		
		if (action.equals("/" + PRProduto.NM_SERVLET_ALTERAR_PRODUTOS)) {
			aPRProduto.alterarProduto(request, response);
		}
		
		if (action.equals("/" + PRProduto.NM_SERVLET_PROCESSAR_ATUALIZAR_PRODUTO)) {
			aPRProduto.processarAlterarProduto(request, response);
		}
		
		if (action.equals("/" + PRProduto.NM_SERVLET_EXCLUIR_PRODUTO)) {
			aPRProduto.processarExcluirProduto(request, response);
		}
		
		if (action.equals("/" + PRProduto.NM_SERVLET_CONSULTAR_PRODUTO_PARAMETRO)) {
			aPRProduto.consultarProdutoParametro(request, response);
		}
		
		// **************** SERVLETS PEDIDOS ***********************/
		if (action.equals("/" + PRPedido.NM_SERVLET_EXIBIR_PEDIDOS)) {
			aPRPedido.exibirPedidos(request, response);
		}
		
		if (action.equals("/" + PRPedido.NM_SERVLET_INCLUIR_PEDIDO)) {
			aPRPedido.incluirPedido(request, response);
		}
		
		if (action.equals("/" + PRPedido.NM_SERVLET_EXIBIR_INCLUIR)) {
		    aPRPedido.exibirIncluir(request, response);
		}
		
		if (action.equals("/" + PRPedido.NM_SERVLET_EXCLUIR_PEDIDO)) {
		    aPRPedido.processarExcluirPedido(request, response);
		}
		
		if (action.equals("/" + PRPedido.NM_SERVLET_DETALHAR_PEDIDO)) {
		    aPRPedido.exibirDetalhamento(request, response);
		}
		
		if (action.equals("/" + PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO)) {
		    aPRPedido.consultarPedidoParametro(request, response);
		}
	}
}
