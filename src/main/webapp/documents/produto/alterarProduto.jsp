<%@ page import="beans.ProdutoBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRProduto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ProdutoBean produto = (ProdutoBean) request.getAttribute(ProdutoBean.NM_ENTIDADE);
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
			<h1>Alterar produto</h1>
			<form name="frmProduto" action="${pageContext.request.contextPath}/<%=PRProduto.NM_SERVLET_PROCESSAR_ATUALIZAR_PRODUTO%>">
				<table>
					<tr>
						<td>
							<input type="text" name="<%=ProdutoBean.NM_COL_IdProduto%>" class="CaixaID" value="<%=produto.getIdProduto()%>" readonly>
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="<%=ProdutoBean.NM_COL_Descricao%>" class="Caixa1" value="<%=produto.getDescricao()%>">*
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="<%=ProdutoBean.NM_COL_Valor%>" class="Caixa1" value="<%=produto.getValor()%>">*
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" name="<%=ProdutoBean.NM_COL_QuantidadeEstoque%>" class="Caixa1" value="<%=produto.getQuantidadeEstoque()%>">*
						</td>
					</tr>
					<tr>
						<td>
							<input type="date" name="<%=ProdutoBean.NM_COL_DtCadastro%>" class="Caixa2" value="<%=produto.getDtCadastro()%>">
						</td>
					</tr>
				</table>
				<input type="button" value="Salvar" class="Botao1" onclick="validarProduto()">
			</form>
		</div>
	</div>
<script src="${pageContext.request.contextPath}/js/validador.js"></script>
</body>
</html>