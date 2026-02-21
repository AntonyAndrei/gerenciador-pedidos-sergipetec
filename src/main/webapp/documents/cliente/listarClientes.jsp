<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="beans.ClienteBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRCliente"%>

<%
	ArrayList<ClienteBean> listaClientes = (ArrayList<ClienteBean>) request.getAttribute(PRCliente.NM_ARRAY_CLIENTE);
%>

<!DOCTYPE html>
<html lang=pt-br>
<head>
<meta charset="UTF-8">
<title>Listar Clientes</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="layout">
	<div class="layoutDentro">
		<h1>Listar Clientes</h1>
		<form action="<%=PRCliente.NM_SERVLET_CONSULTAR_CLIENTE_PARAMETRO%>" >
			<input type="text" name="<%=PRCliente.NM_FILTRO_CLIENTE %>" placeholder="Digite nome ou ID" class="Caixa1" width="300">
			<input type="submit" value="Pesquisar" class="Botao1">
			<a href="<%=PRCliente.NM_SERVLET_EXIBIR_CLIENTES%>" class="BotaoLimpar">Limpar</a>
		</form>
		<table id="tabela">
			<thead>
				<tr>
					<th>Id</th>
					<th>Nome</th>
					<th>E-mail</th>
					<th>Data de Cadastro</th>
					<th>Opções</th>				
				</tr>
			</thead>
			<tbody>
				<%for (ClienteBean cliente : listaClientes){ %>
					<tr>
						<td><%=cliente.getIdCliente()%></td>
						<td><%=cliente.getNome()%></td>
						<td><%=cliente.getEmail()%></td>
						<td><%=cliente.getDtCadastro()%></td>
						<td>
							<a href="<%=PRCliente.NM_SERVLET_ALTERAR_CLIENTES%>?<%=ClienteBean.NM_COL_IdCliente%>=<%=cliente.getIdCliente()%>" class="Botao1">Alterar</a>
							<a href="#" onclick="confirmarExclusaoCliente(<%=cliente.getIdCliente()%>)" class="BotaoExcluir">Excluir</a>
						</td>
					</tr>
				<%} %>
			</tbody>
		</table>
		<div style="margin-top: 30px;">
			<a href="${pageContext.request.contextPath}/<%=PRCliente.NM_JSP_INCLUIR%>" class="Botao1">Incluir Cliente</a>
		</div>
	</div>
</div>
	
<script>
function confirmarExclusaoCliente (pIdCliente){
	let resposta = confirm("Confirma a exclusão desse cliente?");
	if (resposta === true){
		window.location.href = "<%=PRCliente.NM_SERVLET_EXCLUIR_CLIENTE%>?<%=ClienteBean.NM_COL_IdCliente%>=" + pIdCliente;
	}
}
</script>
</body>
</html>