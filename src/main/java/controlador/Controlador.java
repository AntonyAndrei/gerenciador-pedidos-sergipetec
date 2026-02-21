package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import processadorRequisicao.PRCliente;

@WebServlet(urlPatterns = {
		"/Controlador",
		"/" + PRCliente.NM_SERVLET_EXIBIR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_INCLUIR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_ALTERAR_CLIENTES,
		"/" + PRCliente.NM_SERVLET_PROCESSAR_ATUALIZAR_CLIENTE,
		"/" + PRCliente.NM_SERVLET_EXCLUIR_CLIENTE
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
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
	}
}
