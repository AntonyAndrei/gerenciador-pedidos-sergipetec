<%@page import="beans.ProdutoBean"%>
<%@page import="processadorRequisicao.PRProduto"%>
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
			<h1>Cadastrar novo produto</h1>
			<form name="frmProduto" action="${pageContext.request.contextPath}/<%=PRProduto.NM_SERVLET_INCLUIR_PRODUTOS%>">
				<table>
					<tr>
						<td><input type="text" name="<%=ProdutoBean.NM_COL_Descricao%>" placeholder="Descrição do produto" class="Caixa1">*</td>
					</tr>
					<tr>
						<td><input type="text" name="<%=ProdutoBean.NM_COL_Valor%>" placeholder="Valor do produto" class="Caixa1">*</td>				
					</tr>
					<tr>
						<td><input type="text" name="<%=ProdutoBean.NM_COL_QuantidadeEstoque%>" placeholder="Quantidade em estoque" class="Caixa1">*</td>				
					</tr>
				</table>
				<input type="button" value="Adicionar" class="Botao1" onclick="validarProduto()">
				<a href="${pageContext.request.contextPath}/<%=PRProduto.NM_SERVLET_EXIBIR_PRODUTOS%>" class="BotaoLimpar">Voltar</a>
			</form>
		</div>
	</div>

<script src="${pageContext.request.contextPath}/js/validador.js"></script>
</body>
</html>