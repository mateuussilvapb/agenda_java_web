<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Editar contato</title>
<link rel="icon" href="imagens/phone.svg">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Editar contato</h1>
	<form name="frmContato" action="update">
		<table>
			<tr>
				<td>ID</td>
				<td><input type="text" name="idcon" id="caixa_3" readonly
					value="<%out.print(request.getAttribute("idcon"));%>"></td>
			</tr>
			<tr>
				<td>Nome</td>
				<td><input type="text" name="nome" class="caixa_1"
					value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td>Fone</td>
				<td><input type="text" name="fone" class="caixa_2" id="fone"
					value="<%out.print(request.getAttribute("fone"));%>"
					maxlength="15"></td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td><input type="text" name="email" class="caixa_1"
					value="<%out.print(request.getAttribute("email"));%>"></td>
			</tr>
		</table>
		<input type="button" class="btn_1" value="Salvar" onclick="validar()">
	</form>
	<script src="scripts/validador.js"></script>
</body>
</html>