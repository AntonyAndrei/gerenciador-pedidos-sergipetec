package processadorRequisicao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import beans.ClienteBean;
import beans.PedidoBean;
import beans.ProdutoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ClienteDAO;
import model.PedidoDAO;
import model.ProdutoDAO;

public class PRPedido {
    public static final String NM_SERVLET_EXIBIR_PEDIDOS  = "exibirPedidos";
    public static final String NM_SERVLET_INCLUIR_PEDIDO  = "incluirPedido";
    public static final String NM_SERVLET_EXIBIR_INCLUIR  = "exibirIncluir";
    public static final String NM_SERVLET_EXCLUIR_PEDIDO  = "excluirPedido";
    
    public static final String NM_ARRAY_PEDIDO = "listaPedidos";
    public static final String NM_ARRAY_IDS_PRODUTOS_PEDIDO = "idProdutos[]";
    public static final String NM_ARRAY_QUANTIDADES_PRODUTOS_PEDIDO = "quantidades[]";
    public static final String NM_ARRAY_DESCONTOS_PRODUTOS_PEDIDO = "descontos[]";
    
    public static String NM_JSP_LISTAR = "/documents/pedido/listarPedido.jsp";
    public static String NM_JSP_NOVO   = "/documents/pedido/novoPedido.jsp";

    PedidoDAO pedidoDAO = new PedidoDAO();
    ProdutoDAO produtoDAO = new ProdutoDAO();

    public void exibirPedidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<PedidoBean> lista = pedidoDAO.consultarPedidos();
        request.setAttribute(NM_ARRAY_PEDIDO, lista);
        request.getRequestDispatcher(NM_JSP_LISTAR).forward(request, response);
    }

    public void incluirPedido(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtem id do cliente
        int idCliente = Integer.parseInt(request.getParameter(PedidoBean.NM_COL_IdCliente));
        
        // Obtem Dados dos Itens
        String[] idsProdutos = request.getParameterValues(NM_ARRAY_IDS_PRODUTOS_PEDIDO);
        String[] quantidades = request.getParameterValues(NM_ARRAY_QUANTIDADES_PRODUTOS_PEDIDO);
        String[] descontos   = request.getParameterValues(NM_ARRAY_DESCONTOS_PRODUTOS_PEDIDO);
        
        // Inicializ e cria objeto do pedido
        PedidoBean pedido = new PedidoBean();
        pedido.setIdCliente(idCliente);
        pedido.setDtPedido(LocalDate.now());

        // Passa as listas para o DAO
        pedidoDAO.incluirPedido(pedido, idsProdutos, quantidades, descontos);

        response.sendRedirect(NM_SERVLET_EXIBIR_PEDIDOS);
    }
    
    public void exibirIncluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Busca as listas de Cliente e Produtos para popular os selects
        ArrayList<ClienteBean> listaClientes = new ClienteDAO().consultarCliente();
        ArrayList<ProdutoBean> listaProdutos = new ProdutoDAO().consultarProduto();
        
        // Envia as listas para a JSP
        request.setAttribute(PRCliente.NM_ARRAY_CLIENTE, listaClientes);
        request.setAttribute(PRProduto.NM_ARRAY_PRODUTO, listaProdutos);
        
        request.getRequestDispatcher(NM_JSP_NOVO).forward(request, response);
    }
}