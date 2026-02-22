<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="beans.BeanGenerico"%>
<%@ page import="beans.PedidoBean"%>
<%@ page import="beans.ClienteBean"%>
<%@ page import="beans.ItemPedidoBean"%>
<%@ page import="beans.ProdutoBean"%>
<%@ page import="processadorRequisicao.PRPedido"%>
<%@ page import="java.util.ArrayList"%>

<%
    BeanGenerico beanGenericoPedidoDetalhe = (BeanGenerico) request.getAttribute(PRPedido.NM_ATR_PEDIDO_DETALHE);
    ArrayList<BeanGenerico> listaItensPedidoGenericos = (ArrayList<BeanGenerico>) request.getAttribute(PRPedido.NM_ATR_LISTA_ITENS);
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Detalhar Pedido</title>
<link rel="icon" href="${pageContext.request.contextPath}/imagens/iconePedido.png">
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="layout">
    <div class="layoutDentro">
        <h1>Pedido #<%= beanGenericoPedidoDetalhe.getAtribute(PedidoBean.NM_COL_IdPedido) %></h1>
        
        <div class="info-cabecalho">
            <p><strong>Código do Pedido:</strong> <%= beanGenericoPedidoDetalhe.getAtribute(PedidoBean.NM_COL_IdPedido) %></p>
            <p><strong>Nome do Cliente:</strong> <%= beanGenericoPedidoDetalhe.getAtribute(ClienteBean.NM_COL_Nome) %></p>
            <p><strong>Data da Realização:</strong> <%= beanGenericoPedidoDetalhe.getAtribute(PedidoBean.NM_COL_DtPedido) %></p>
        </div>

        <h3>Itens Cadastrados no Pedido</h3>
        <table id="tabela">
            <thead>
                <tr>
                    <th>ID Item</th>
                    <th>Descrição do Produto</th>
                    <th>Valor Unitário</th>
                    <th>Quantidade</th>
                    <th>Desconto Aplicado</th>
                    <th>Subtotal</th>
                </tr>
            </thead>
            <tbody>
                <% 
                double valorTotalAcumulado = 0;
                
                for (BeanGenerico beanGenericoItem : listaItensPedidoGenericos) { 
                    double valorUnitarioItem = (Double) beanGenericoItem.getAtribute(ItemPedidoBean.NM_COL_ValorUnitario);
                    int quantidadeVendida    = (Integer) beanGenericoItem.getAtribute(ItemPedidoBean.NM_COL_Quantidade);
                    double valorDescontoItem = (Double) beanGenericoItem.getAtribute(ItemPedidoBean.NM_COL_Desconto);
                    
                    double valorSubtotalItem = (valorUnitarioItem * quantidadeVendida) - valorDescontoItem;
                    valorTotalAcumulado += valorSubtotalItem;
                %>
                    <tr>
                        <td><%= beanGenericoItem.getAtribute(ItemPedidoBean.NM_COL_IdItem) %></td>
                        <td><%= beanGenericoItem.getAtribute(ProdutoBean.NM_COL_Descricao) %></td>
                        <td>R$ <%= String.format("%.2f", valorUnitarioItem) %></td>
                        <td><%= quantidadeVendida %></td>
                        <td>R$ <%= String.format("%.2f", valorDescontoItem) %></td>
                        <td>R$ <%= String.format("%.2f", valorSubtotalItem) %></td>
                    </tr>
                <% } %>
            </tbody>
            <tfoot>
                <tr style="background-color: #eee; font-weight: bold;">
                    <td colspan="5" style="text-align: right;">VALOR TOTAL DO PEDIDO:</td>
                    <td>R$ <%= String.format("%.2f", valorTotalAcumulado) %></td>
                </tr>
            </tfoot>
        </table>

        <div class="rodape-formulario" style="margin-top: 20px;">
            <a href="<%=PRPedido.NM_SERVLET_EXIBIR_PEDIDOS%>" class="BotaoLimpar">Voltar</a>
        </div>
    </div>
</div>
</body>
</html>