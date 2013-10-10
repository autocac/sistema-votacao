<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<table border="0" cellpadding="5" >

<%-- 	<c:if test="${user.tipo.codigo == 'A'}"> --%>
	<c:if test="${fn:contains(user.tipo.codigo, 'A')}">
		<tr>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/VotacaoServlet">Nova Votação</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/EditarVotacaoServlet">Editar Votação</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/EleitorServlet">Novo Eleitor</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/GeradorEleitoresServlet">Gerar Eleitores</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/GeradorEleitoresServlet?acao=listar">Relatório de Eleitores que Faltam Trocar Senha</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/ListaEleitoresServlet">Lista Eleitores</a>
			</td>
			<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
				<a href="/SistemaVotacao/ResultadosServlet">Resultados</a>
			</td>
		</tr>
		<tr>
			<td colspan="5"></td>
		</tr>
	</c:if>
	
	
	<tr>
		<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
			<a href="/SistemaVotacao/ListaVotacoesServlet">Lista Votações</a>
		</td>
		<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
			<a href="/SistemaVotacao/restrito/eleitor/trocarSenha.jsp">Trocar Senha</a>
		</td>
		<td align="char" style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
			<a href="/SistemaVotacao/LogoutServlet">Sair</a>
		</td>
	</tr>
</table>

<h1>Sistema Votação</h1>