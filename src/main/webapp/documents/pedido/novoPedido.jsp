<%@page import="processadorRequisicao.PRProduto"%>
<%@page import="processadorRequisicao.PRCliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.ClienteBean"%>
<%@ page import="beans.ProdutoBean"%>
<%@ page import="beans.PedidoBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="processadorRequisicao.PRPedido"%>

<%
    ArrayList<ClienteBean> listaClientes = (ArrayList<ClienteBean>) request.getAttribute(PRCliente.NM_ARRAY_CLIENTE);
    ArrayList<ProdutoBean> listaProdutos = (ArrayList<ProdutoBean>) request.getAttribute(PRProduto.NM_ARRAY_PRODUTO);
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Novo Pedido</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="layout">
    <div class="layoutDentro">
        <h1>Cadastrar Novo Pedido</h1>
        
        <form name="frmPedido" action="${pageContext.request.contextPath}/<%=PRPedido.NM_SERVLET_INCLUIR_PEDIDO%>" method="post">
            <div style="margin-bottom: 20px;">
                <label>Cliente:</label>
                <select name="<%=PedidoBean.NM_COL_IdCliente%>" class="Caixa1" required>
                    <option value="">Selecione um cliente</option>
                    <% for(ClienteBean cliente : listaClientes) { %>
                        <option value="<%=cliente.getIdCliente()%>"><%=cliente.getNome()%></option>
                    <% } %>
                </select>
            </div>

            <hr>
            <h3>Produtos do Pedido</h3>
            
            <div id="container-itens">
                <div class="linha-produto" id="item-base">
                    <table>
                        <tr>
                            <td>Produto:</td>
                            <td>
                                <select name="<%=PRPedido.NM_ARRAY_IDS_PRODUTOS_PEDIDO%>" class="Caixa1 select-produto" required onchange="atualizarPreco(this)">
                                    <option value="" data-preco="0">Selecione o produto</option>
                                    <% for(ProdutoBean produto : listaProdutos) { %>
                                        <option value="<%=produto.getIdProduto()%>" data-preco="<%=produto.getValor()%>">
                                            <%=produto.getDescricao()%> (R$ <%=produto.getValor()%>)
                                        </option>
                                    <% } %>
                                </select>
                            </td>
                            <td>Valor do item: <span class="exibir-preco">0.00</span></td>
                        </tr>
                        <tr>
                            <td>Quantidade: <input type="number" name="<%=PRPedido.NM_ARRAY_QUANTIDADES_PRODUTOS_PEDIDO%>" class="Caixa2" min="1" value="1" required></td>
                            <td>Desconto: <input type="number" name="<%=PRPedido.NM_ARRAY_DESCONTOS_PRODUTOS_PEDIDO%>" class="Caixa2" min="0" step="0.01" value="0.00"></td>
                            <td><span class="remover-item" onclick="removerLinha(this)">[X] Remover</span></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="secao-botoes-acao">
                <button type="button" class="Botao1 BotaoVerde" onclick="adicionarLinha()">+ Adicionar Produto</button>
            </div>

            <div class="rodape-formulario">
                <input type="submit" value="Finalizar Pedido" class="Botao1">
                <a href="<%=PRPedido.NM_SERVLET_EXIBIR_INCLUIR%>" class="BotaoExcluir">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<script>
// Função para atualizar o texto do preço unitário ao selecionar um produto
function atualizarPreco(select) {
    let preco = select.options[select.selectedIndex].getAttribute('data-preco');
    let linha = select.closest('table');
    linha.querySelector('.exibir-preco').innerText = parseFloat(preco).toFixed(2);
}

// Função para clonar a linha de produto
function adicionarLinha() {
    let container = document.getElementById('container-itens');
    let novaLinha = document.getElementById('item-base').cloneNode(true);
    
    // Limpa os valores da nova linha
    novaLinha.id = ""; 
    novaLinha.querySelector('select').selectedIndex = 0;
    novaLinha.querySelector('.exibir-preco').innerText = "0.00";
    let inputs = novaLinha.querySelectorAll('input');
 	// quantidade
    inputs[0].value = 1; 
 	// desconto
    inputs[1].value = 0.00; 
    // coloca html no document
    container.appendChild(novaLinha);
}

// Função para remover linha (não remove se for a única)
function removerLinha(botao) {
    let linhas = document.querySelectorAll('.linha-produto');
    if(linhas.length > 1) {
        botao.closest('.linha-produto').remove();
    } else {
        alert("O pedido deve ter pelo menos um produto!");
    }
}
</script>
</body>
</html>