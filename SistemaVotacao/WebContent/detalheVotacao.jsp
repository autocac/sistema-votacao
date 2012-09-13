<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votação ${votacao.id} - Sistema Votação</title>
		<script type="Javascript">
			var clicado = 0;
			function votar(idVotacao, idCandidato) {
				if (clicado == 0) {
					clicado = 1;
					document.getElementById('idVotacao').value = idVotacao;
					document.getElementById('idCandidato').value = idCandidato;

					document.frmVotacao.submit();
				} else {
					alert('Aguarde, operação em processamento');
				}
				return false;
			}
		</script>
	</head>
	<body>
		<c:if test="${user.tipo eq Usuario.Tipo.ADMINISTRADOR}">
			<table border="1">
				<tr>
					<td>
						<a href="/NovaVotacaoServlet">Nova Votacao</a>
					</td>
					<td>
						<a href="/NovoEleitorServlet">Novo Eleitor</a>
					</td>
				</tr>
			</table>
		</c:if>
		
		<h1>Votação ${votacao.id} - Sistema Votação</h1>
		
		<c:choose>
			<c:if test="${votacao.encerrada}">
				
				<h2>Votação ${votacao.descricao} Encerrada</h2>
				
			</c:if>
			<c:otherwise>
			
				<h2>${votacao.descricao}</h2>
				<h3>${msg}</h3>
				<h4>Término da votação : ${votacao.periodo.dataFim}</h4>
				
				<h5>Vote em um dos candidatos abaixo</h5>
				
				<ul>
					<c:forEach var="candidado" items="${votacao.candidatos}">
						<li>
							<a href="#" onClick="javascript:votar(${votacao.id}, ${candidado.id});">
							${candidado.id} - ${candidado.descricao}</a>
						</li>
					</c:forEach>
				</ul>
							
			</c:otherwise>
		</c:choose>
		
		<form id="frmVotacao" name="frmVotacao" action="VotacaoServlet">
			<input type="hidden" id="idVotacao" name="idVotacao" />
			<input type="hidden" id="idCandidato" name="idCandidato" />
		</form>
		
	</body>
</html>