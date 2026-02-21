<%@ page import="beans.ClienteBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRCliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ClienteBean cliente = (ClienteBean) request.getAttribute(ClienteBean.NM_ENTIDADE);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gerenciador de Pedidos</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
	<div class="layout">
		<div class="layoutDentro">
			<h1>Alterar cliente</h1>
			<form name="frmCliente" action="${pageContext.request.contextPath}/<%=PRCliente.NM_SERVLET_PROCESSAR_ATUALIZAR_CLIENTE%>">
				<table>
					<tr>
						<td>
							<input type="text" name="<%=ClienteBean.NM_COL_IdCliente%>" class="CaixaID" value="<%=cliente.getIdCliente()%>" readonly>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="<%=ClienteBean.NM_COL_Nome%>" class="Caixa1" value="<%=cliente.getNome()%>">*
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="<%=ClienteBean.NM_COL_Email%>" class="Caixa1" value="<%=cliente.getEmail()%>">
						</td>
					</tr>
					<tr>
						<td>
							<input type="date" name="<%=ClienteBean.NM_COL_DtCadastro%>" class="Caixa2" value="<%=cliente.getDtCadastro()%>">
						</td>
					</tr>
				</table>
				<input type="submit" value="Salvar" class="Botao1" onclick="validarCliente()">
			</form>
		</div>
	</div>
<script src="${pageContext.request.contextPath}/js/validador.js"></script>
</body>
</html>