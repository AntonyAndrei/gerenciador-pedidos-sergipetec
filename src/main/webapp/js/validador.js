/**
 * Classe JS contendo validações dos formulários do projeto de Gerenciador de Pedidos.
 * @author Antony Andrei de Souza Santos
 */

function validarCliente(){
	let nome = frmCliente.nomeCliente.value;
	if(nome === ""){
		alert("Preencha o campo nome do cliente!");
		frmCliente.nomeCliente.focus();	
		return false;
	} else {
		document.forms["frmCliente"].submit();
	}
}