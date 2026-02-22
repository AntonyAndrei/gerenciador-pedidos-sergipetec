<%@page import="processadorRequisicao.PRProduto"%>
<%@ page import="processadorRequisicao.PRCliente"%>
<%@page import="processadorRequisicao.PRPedido"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Gerenciador de Pedidos</title>
<link rel="icon" href="../imagens/iconePedido.png">
<link rel="stylesheet" href="../style.css">
</head>
<body>
	<div class="layout">
		<div class="layoutDentro">
			<img src="../imagens/iconePedido.png">
			<h1>Gerenciador de Pedidos</h1>
			<a href="${pageContext.request.contextPath}/<%=PRCliente.NM_SERVLET_EXIBIR_CLIENTES%>" class="Botao1">Exibir Clientes</a>
			<a href="${pageContext.request.contextPath}/<%=PRProduto.NM_SERVLET_EXIBIR_PRODUTOS%>" class="Botao1">Exibir Produtos</a>
			<a href="${pageContext.request.contextPath}/<%=PRPedido.NM_SERVLET_EXIBIR_PEDIDOS%>"   class="Botao1">Exibir Pedidos</a>		
		</div>
	</div>
</body>
</html>