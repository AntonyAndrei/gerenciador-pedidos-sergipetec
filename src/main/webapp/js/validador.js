/**
 * Classe JS contendo validações dos formulários do projeto de Gerenciador de Pedidos.
 * @author Antony Andrei de Souza Santos
 */

function validarCliente(){
	let nome = frmCliente.nome.value;
	if(nome === ""){
		alert("Preencha o campo nome do cliente!");
		frmCliente.nomeCliente.focus();	
		return false;
	} else {
		document.forms["frmCliente"].submit();
	}
}

function validarProduto() {
    // Validação da Descrição
    let desc = frmProduto.descricao.value;
    if (desc === "") {
        alert("Preencha a descrição do produto!");
        frmProduto.descricaoProduto.focus();
        return false;
    }

    // Validação do Valor
    let valor = frmProduto.valor.value;
    if (valor === "") {
        alert("Preencha o valor do produto!");
        frmProduto.valorProduto.focus();
        return false;
    }

    // Validação da Quantidade
    let qtd = frmProduto.quantidadeEstoque.value;
    if (qtd === "") {
        alert("Preencha a quantidade em estoque!");
        frmProduto.quantidadeProduto.focus();
        return false;
    }

    // Se passar por todos, envia o formulário
    document.frmProduto.submit();
}