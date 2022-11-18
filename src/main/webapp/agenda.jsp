<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<%
ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
String ehTelaEditarOuDeletarContato = "0";
if ("S".equalsIgnoreCase(response.getHeader("retornoOk"))) {
	ehTelaEditarOuDeletarContato = "S";
} else if ("N".equalsIgnoreCase(response.getHeader("retornoOk"))) {
	ehTelaEditarOuDeletarContato = "N";
} else if ("E".equalsIgnoreCase(response.getHeader("retornoOk"))) {
	ehTelaEditarOuDeletarContato = "E";
}
%>
<script language="JavaScript" src="scripts/alert.js"></script>
<script language="JavaScript" src="scripts/confirmador.js"></script>

<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de Contatos</title>
<link rel="icon" href="imagens/phone.svg">
<link rel="stylesheet" href="style.css">
</head>
<body onload="exibirAlerta('<%=ehTelaEditarOuDeletarContato%>')">
	<h1>Agenda de Contatos</h1>
	<a class="btn_1" href="novo.html">Novo Contato</a>
	<a class="btn_2" href="report">Relatório</a>
	<table id="tabela">
		<thead>
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Fone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (int i = 0; i < lista.size(); i++) {
			%>
			<tr>
				<td><%=lista.get(i).getIdcon()%></td>
				<td class="td_campos"><%=lista.get(i).getNome()%></td>
				<td class="td_campos"><%=lista.get(i).getFone()%></td>
				<td class="td_campos"><%=lista.get(i).getEmail()%></td>
				<td class="td_btns"><a
					href="select?idcon=<%=lista.get(i).getIdcon()%>" class="btn_1">Editar</a>
					<a
					href="javascript: confirmar('<%=lista.get(i).getIdcon()%>', '<%=lista.get(i).getNome()%>')"
					class="btn_2">Excluir</a></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>