<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Vota��o ${votacao.id} - Sistema Vota��o</title>
		<script>
			var clicado = 0;
			function votar(idVotacao, idCandidato) {
				if (clicado == 0) {
					clicado = 1;
					document.getElementById('idVotacao').value = idVotacao;
					document.getElementById('idCandidato').value = idCandidato;

					document.frmVotacao.submit();
				} else {
					alert('Aguarde, opera��o em processamento');
				}
				return false;
			}
		</script>
	</head>
	<body>
		<jsp:include page="../../menu.jsp" />
		
		<h1 align="center">Vota��o ${votacao.id}</h1>
		
		<c:choose>
			<c:when test="${votacao.encerrada}">
				<h2 align="center">Vota��o ${votacao.descricao} Encerrada</h2>
			</c:when>
			<c:otherwise>
			
				<h2 align="center">${votacao.descricao}</h2>
				<h3 align="center">${msg}</h3>
				<h4 align="center">
					T�rmino da vota��o : <fmt:formatDate value="${votacao.periodo.dataFim}" pattern="dd/MM/yyyy HH:mm:ss"/>
				</h4>
				
				<h5 align="center">Vote em um dos candidatos abaixo</h5>
				
				<table align="center">
					<tr>
						<td>
				
							<ul>
								<c:forEach var="candidado" items="${votacao.candidatos}">
									<li>
										<a href="#" onclick="javascript:votar(${votacao.id}, ${candidado.id});">
										${candidado.id} - ${candidado.descricao} - Votos : ${candidado.numeroVotos}</a>
									</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
		
		<form id="frmVotacao" name="frmVotacao" action="/SistemaVotacao/VotacaoServlet">
			<input type="hidden" id="idVotacao" name="idVotacao" />
			<input type="hidden" id="idCandidato" name="idCandidato" />
		</form>
		
	</body>
</html>