/**
 * Confirmação de exclusão de um contato
 @author Mateus Dias
 */

function confirmar(idcon, nome) {
	let resposta = confirm("Confirma a exclusão do contato " + nome + " ?");
	if (resposta === true) {
		window.location.href = "delete?idcon=" + idcon;
	}
}