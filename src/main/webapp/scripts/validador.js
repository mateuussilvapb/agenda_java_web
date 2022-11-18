/**
 * Validação de formulário
 * @author Mateus Dias
 */

function validar() {
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	let email = frmContato.email.value
	if (nome === "") {
		alert("Preencha o campo Nome")
		frmContato.nome.focus()
		return false
	}
	else if (fone === "") {
		alert("Preencha o campo Fone")
		frmContato.fone.focus()
		return false
	} else if(nome.length > 50){
		alert("Preencha o campo Informe um nome com menos de 50 caracteres")
		frmContato.nome.focus()
		return false
	} else if (fone.lenght > 15) {
		alert("Informe um telefone com menos de 15 caracteres")
		frmContato.fone.focus()
		return false
	}   else if (email.lenght > 50) {
		alert("Informe um email com menos de 50 caracteres")
		frmContato.fone.focus()
		return false
	} 
	else {
		document.forms["frmContato"].submit()
	}
}