<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votações - Sistema Votação</title>
		<script language="JavaScript" type="text/javascript">
			var clicado = 0;
		
			String.prototype.trim = function () {
			    return this.replace(/^\s*/, "").replace(/\s*$/, "");
			}
		
			function apagar() {
				if (clicado == 0) {
					clicado = 1;
					
					document.getElementById('acao').value = 'apagar';
					
					document.frmEleitor.submit();
				} else {
					alert('Aguarde, operação em processamento');
				}
				return false;
			}
			
			function salvar() {
				if (clicado == 0) {
					clicado = 1;
					
					var txtLogin = document.getElementById('login');
					var txtSenha = document.getElementById('senha');
					var txtNome = document.getElementById('nome');
					
					if (!isIdentificador(txtLogin, 'Login')) {
						return false;
					}
					
					if (!isIdentificador(txtSenha, 'Senha')){
						return false;
					}
					
					if (isEmpty(txtNome, 'Nome')){
						return false;
					}

					document.frmEleitor.submit();
				} else {
					alert('Aguarde, operação em processamento');
				}
				return false;
			}
			
			function isIdentificador(campo, nomeCampo) {
				
				if (contains(campo.value.trim(), ' ')) {
					alert(nomeCampo + ' não pode ter caracter em branco no meio');
					clicado = 0;
					campo.focus();
					return false;
				}
				
				return !isEmpty(campo, nomeCampo);
			}
			
			function isEmpty(campo, nomeCampo) {
				if (campo.value.trim().length == 0) {
					alert(nomeCampo + ' não pode ser vazio');
					clicado = 0;
					campo.focus();
					return true;
				}
				return false;
			}
			
			function contains(str, caracter) {
				var ret = false;
				var i = 0;
				for (;i < str.length && !ret; i++) {
					ret = ret || (str.charAt(i) == caracter);
				}
				return ret;
			}
			
		</script>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Eleitor</h1>
		<h2>${msg}</h2>
	
		<%
			String login = (String)request.getAttribute("login");
			if (login == null || login.trim().equals("")) {
				request.setAttribute("acao", "criar");
				request.setAttribute("loginState", "");
			} else {
				request.setAttribute("acao", "salvar");
				request.setAttribute("loginState", "readonly");
			}
			
		%>
	
		<form id="frmEleitor" name="frmEleitor" action="/SistemaVotacao/EleitorServlet">
			<table align="center">
				<tr>
					<td>
						<input type="hidden" id="acao" name="acao" value="${acao}" />
					</td>
					<td>
						Login : 
					</td>
					<td>
						<c:choose>
							<c:when test="${loginState eq 'readonly'}">
								<input type="text" id="login" name="login" size="10" readonly="readonly" value="${login}" />
							</c:when>
							<c:otherwise>
								<input type="text" id="login" name="login" size="10" value="${login}" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						Senha : 
					</td>
					<td>
						<input type="password" id="senha" name="senha" size="10" value="${senha}" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						Nome : 
					</td>
					<td>
						<input type="text" id="nome" name="nome" size="25" value="${nome}" />
					</td>
				</tr>
				<tr>
					<td>
						<c:choose>
							<c:when test="${loginState eq 'readonly'}">
								<input type="button" id="btnAgapar" name="btnAgapar" value="Apagar" onclick="javascript:apagar()" /> 
							</c:when>
						</c:choose>
					</td>
					<td>
						<input type="button" id="btnCancelar" name="btnCancelar" value="Cancelar" /> 
					</td>
					<td>
						<input type="button" id="btnSalvar" name="btnSalvar" value="Salvar" onclick="javascript:salvar()"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>