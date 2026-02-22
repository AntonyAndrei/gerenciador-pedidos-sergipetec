<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="beans.PedidoBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRPedido"%>

<%
    ArrayList<PedidoBean> listaPedidos = (ArrayList<PedidoBean>) request.getAttribute(PRPedido.NM_ARRAY_PEDIDO);
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Listar Pedidos</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="layout">
    <div class="layoutDentro">
        <h1>Listar Pedidos</h1>
        
        <form action="#">
            <input type="text" name="filtroPedido" placeholder="Digite ID do Pedido" class="Caixa1" width="300">
            <input type="submit" value="Pesquisar" class="Botao1">
            <a href="<%=PRPedido.NM_SERVLET_EXIBIR_PEDIDOS%>" class="BotaoLimpar">Limpar</a>
        </form>

        <table id="tabela">
            <thead>
                <tr>
                    <th>Id Pedido</th>
                    <th>Id Cliente</th>
                    <th>Data do Pedido</th>
                    <th>Opções</th>                
                </tr>
            </thead>
            <tbody>
                <% if (listaPedidos != null) { 
                    for (PedidoBean pedido : listaPedidos) { %>
                    <tr>
                        <td><%=pedido.getIdPedido()%></td>
                        <td><%=pedido.getIdCliente()%></td>
                        <td><%=pedido.getDtPedido()%></td>
                        <td>
                            <a href="#" onclick="confirmarExclusaoPedido(<%=pedido.getIdPedido()%>)" class="BotaoExcluir">Excluir</a>
                        	<a href="<%=PRPedido.NM_SERVLET_DETALHAR_PEDIDO%>?<%=PedidoBean.NM_COL_IdPedido%>=<%=pedido.getIdPedido()%>" class="Botao1">Detalhar</a>
                        </td>
                    </tr>
                <%  } 
                } %>
            </tbody>
        </table>

        <div class="rodape-formulario">
			<a href="${pageContext.request.contextPath}/<%=PRPedido.NM_SERVLET_EXIBIR_INCLUIR%>" class="Botao1">Novo Pedido</a>
			<a href="${pageContext.request.contextPath}/documents/index.jsp" class="BotaoLimpar">Voltar</a>
        </div>
    </div>
</div>
    
<script>
function confirmarExclusaoPedido(pIdPedido) {
    let resposta = confirm("Deseja realmente excluir este pedido?");
    if (resposta === true) {
        window.location.href = "<%=PRPedido.NM_SERVLET_EXCLUIR_PEDIDO%>?<%=PedidoBean.NM_COL_IdPedido%>=" + pIdPedido;
    }
}
</script>
</body>
</html>