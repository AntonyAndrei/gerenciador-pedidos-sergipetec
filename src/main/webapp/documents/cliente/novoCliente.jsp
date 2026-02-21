<%@page import="processadorRequisicao.PRCliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Gerenciador de Pedidos</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
	<div class="layout">
		<div class="layoutDentro">
			<h1>Cadastrar novo cliente</h1>
			<form name="frmCliente" action="${pageContext.request.contextPath}/<%=PRCliente.NM_SERVLET_INCLUIR_CLIENTES%>">
				<table>
					<tr>
						<td><input type="text" name="nomeCliente" placeholder="Nome do cliente" class="Caixa1">*</td>
					</tr>
					<tr>
						<td><input type="text" name="emailCliente" placeholder="E-mail do cliente" class="Caixa1"></td>				
					</tr>
				</table>
				<input type="button" value="Adicionar" class="Botao1" onclick="validarCliente()">
			</form>
		</div>
	</div>

<script src="${pageContext.request.contextPath}/js/validador.js"></script>
</body>
</html>