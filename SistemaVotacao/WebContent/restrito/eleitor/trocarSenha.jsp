<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Login - Sistema Votação</title>
		<script language="JavaScript" type="text/javascript">
			function voltar() {
				document.frmLogin.action = '/SistemaVotacao/ListaVotacoesServlet';
				document.frmLogin.submit();
			}
		</script>
	</head>
	<body>
		<h1>Trocar de Senha - Sistema Votação</h1>
		<h2>${msg}</h2>
		<%
			String action = (String)request.getAttribute("action");
			if (action == null || action.endsWith("jsp")) {
				action = "/SistemaVotacao/ListaVotacoesServlet";
			}
			request.setAttribute("action", action);
		%>
		<form id="frmLogin" name="frmLogin" action="${action}" method="post">
			<table
				style="border-collapse: collapse; border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000; border-bottom: 3px solid #000000; width: 300px; height: 200px;"
				align="center">
				<tr>
					<td align="right">Usuário :</td>
					<td><input type="text" id="user" name="user" size="15" readonly="readonly" value="${user.login}" /></td>
				</tr>
				<tr>
					<td align="right">Nova Senha:</td>
					<td><input type="password" id="newPass1" name="newPass1" size="15" /></td>
				</tr>
				<tr>
					<td align="right">Confirmação de Senha:</td>
					<td><input type="password" id="newPass2" name="newPass2" size="15" /></td>
				</tr>
				<tr>
					<td>
						<input type="button" id="btnVoltar" name="btnVoltar" value="Voltar" onclick="javascript:voltar();return false;"/>
					</td>
					<td><input type="submit" id="btnOk" name="btnOk" value="OK" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>