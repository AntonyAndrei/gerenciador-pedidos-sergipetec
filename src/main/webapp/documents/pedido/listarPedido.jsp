<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="processadorRequisicao.PRProduto"%>
<%@page import="processadorRequisicao.PRCliente"%>
<%@page import="beans.ProdutoBean"%>
<%@page import="beans.ClienteBean"%>
<%@ page import="beans.PedidoBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRPedido"%>

<%
    ArrayList<PedidoBean> listaPedidos = (ArrayList<PedidoBean>) request.getAttribute(PRPedido.NM_ARRAY_PEDIDO);
    ArrayList<ClienteBean> listaClientesFiltro = (ArrayList<ClienteBean>) request.getAttribute(PRCliente.NM_ARRAY_CLIENTE);
    ArrayList<ProdutoBean> listaProdutosFiltro = (ArrayList<ProdutoBean>) request.getAttribute(PRProduto.NM_ARRAY_PRODUTO);
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
	    
        <div class="secao-filtros">
        	<form action="<%=PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO%>" method="post" class="filtro-form">
		        <label>ID Pedido:</label>
		        <input type="number" name="<%=PedidoBean.NM_COL_IdPedido%>" class="Caixa1" style="width: 350px;" placeholder="Digite o ID do Pedido">
		        <input type="submit" value="Buscar ID" class="Botao1">
		    </form>
		    <form action="<%=PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO%>" method="post" class="filtro-form">
		        <label>Período:</label>
		        <input type="date" name="dtInicio" class="Caixa1" style="width: 165px;">
		        <span style="color: #4682B4; font-weight: bold;"> até </span>
		        <input type="date" name="dtFim" class="Caixa1" style="width: 165px;">
		        <input type="submit" value="Filtrar Datas" class="Botao1">
		    </form>
            <form action="<%=PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO%>" method="post" class="filtro-form">
                <label>Filtrar Cliente:</label>
                <select name="<%=PedidoBean.NM_COL_IdCliente%>" class="Caixa1">
                    <option value="">-- Todos os Clientes --</option>
                    <% if(listaClientesFiltro != null) { 
                       		for(ClienteBean cliente : listaClientesFiltro) { %>
                            		<option value="<%=cliente.getIdCliente()%>"><%=cliente.getNome()%></option>
                    <% 		}
                      } %>
                </select>
                <input type="submit" value="Filtrar Cliente" class="Botao1">
            </form>
        
            <form action="<%=PRPedido.NM_SERVLET_CONSULTAR_PEDIDO_PARAMETRO%>" method="post" class="filtro-form">
                <label>Filtrar Produto:</label>
                <select name="<%=ProdutoBean.NM_COL_IdProduto%>" class="Caixa1">
                    <option value="">-- Todos os Produtos --</option>
                    <% if(listaProdutosFiltro != null) { 
                        	for(ProdutoBean produto : listaProdutosFiltro) { %>
                            <option value="<%=produto.getIdProduto()%>"><%=produto.getDescricao()%></option>
                    <% 		} 
                       } %>
                </select>
                <input type="submit" value="Filtrar Produto" class="Botao1">
            </form>
        </div>
        <a href="<%=PRPedido.NM_SERVLET_EXIBIR_PEDIDOS%>" class="BotaoLimpar">Limpar Filtros</a>

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