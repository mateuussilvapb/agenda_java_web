function exibirAlerta(checagem) {
	console.log(checagem);
	if (checagem === "S") {
		window.alert("Contato alterado com sucesso!");
	} else if (checagem === "E") {
		window.alert("Contato excluído com sucesso!");
	}
	else if (checagem === "N") {
		window
			.alert("Ocorreu um erro durante o procedimento. Tente novamente.");
	}
}