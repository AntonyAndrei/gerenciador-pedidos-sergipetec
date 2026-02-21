<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="beans.ProdutoBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRProduto"%>

<%
	ArrayList<ProdutoBean> listaProdutos = (ArrayList<ProdutoBean>) request.getAttribute("listaProdutos");
%>

<!DOCTYPE html>
<html lang=pt-br>
<head>
<meta charset="UTF-8">
<title>Listar Produtos</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="layout">
	<div class="layoutDentro">
		<h1>Listar Produtos</h1>
		<table id="tabela">
			<thead>
				<tr>
					<th>Id</th>
					<th>Descrição</th>
					<th>Valor</th>
					<th>Quantidade</th>
					<th>Data de Cadastro</th>
					<th>Opções</th>				
				</tr>
			</thead>
			<tbody>
				<%for (ProdutoBean produto : listaProdutos){ %>
					<tr>
						<td><%=produto.getIdProduto()%></td>
						<td><%=produto.getDescricao()%></td>
						<td><%=produto.getValor()%></td>
						<td><%=produto.getQuantidadeEstoque()%></td>
						<td><%=produto.getDtCadastro()%></td>
						<td>
							<a href="<%=PRProduto.NM_SERVLET_ALTERAR_PRODUTOS%>?<%=ProdutoBean.NM_COL_IdProduto%>=<%=produto.getIdProduto()%>" class="Botao1">Alterar</a>
							<a href="#" onclick="confirmarExclusaoProduto(<%=produto.getIdProduto()%>)" class="BotaoExcluir">Excluir</a>
						</td>
					</tr>
				<%} %>
			</tbody>
		</table>
		<div style="margin-top: 30px;">
			<a href="${pageContext.request.contextPath}/<%=PRProduto.NM_JSP_INCLUIR%>" class="Botao1">Incluir Produto</a>
		</div>
	</div>
</div>
	
<script>
function confirmarExclusaoProduto (pIdProduto){
	let resposta = confirm("Confirma a exclusão desse produto?");
	if (resposta === true){
		window.location.href = "<%=PRProduto.NM_SERVLET_EXCLUIR_PRODUTO%>?<%=ProdutoBean.NM_COL_IdProduto%>=" + pIdProduto;
	}
}
</script>
</body>
</html>