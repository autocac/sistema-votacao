<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votação ${votacao.id} - Sistema Votação</title>
		<script>
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
		<jsp:include page="/menu.jsp" />
		
		<h1 align="center">Votação ${votacao.id}</h1>
		
		<c:choose>
			<c:when test="${votacao.encerrada}">
				<h2 align="center">Votação '${votacao.descricao}' Encerrada</h2>
			</c:when>
			<c:otherwise>
			
				<h2 align="center">${votacao.descricao}</h2>
				<h3 align="center">${msg}</h3>
				<h4 align="center">
					Início da votação : <fmt:formatDate value="${votacao.periodo.dataInicio}" pattern="dd/MM/yyyy HH:mm:ss"/>
				</h4>
				<h4 align="center">
					Término da votação : <fmt:formatDate value="${votacao.periodo.dataFim}" pattern="dd/MM/yyyy HH:mm:ss"/>
				</h4>
				
				<h4 align="center">Para votar em um candidato, clique em 'Votar' ao lado do candidato</h4>
				
				<table align="center">
					<c:forEach var="candidado" items="${votacao.candidatos}">
						<tr>
							<td colspan="2">
								<b>${candidado.nome}</b>
							</td>
						</tr>
						<tr>
							<td>
								<div>	
									<img src="/SistemaVotacao/GetImageServlet?idVotacao=${votacao.id}&idCandidato=${candidado.id}&random=${random}" width="300" height="250" />
								</div>
							</td>
							<td>
								<input type="button" id="btnVotar" name="btnVotar" onclick="javascript:votar(${votacao.id}, ${candidado.id});" value="Votar"/>
<%-- 								<a href="#" onclick="javascript:votar(${votacao.id}, ${candidado.id});">Votar</a> --%>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								${candidado.id} - ${candidado.descricao} - Votos : ${candidado.numeroVotos}
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		
		<form id="frmVotacao" name="frmVotacao" action="/SistemaVotacao/ConclusaoVotacaoServlet">
			<input type="hidden" id="idVotacao" name="idVotacao" />
			<input type="hidden" id="idCandidato" name="idCandidato" />
		</form>
		
	</body>
</html>