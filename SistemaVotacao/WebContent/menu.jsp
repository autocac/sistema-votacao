<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border="1">
	<tr>
		<c:if test="${user.tipo.codigo eq 'A'}">
			<td>
				<a href="/SistemaVotacao/ListaVotacoesServlet">Lista Votações</a>
			</td>
			<td>
				<a href="/SistemaVotacao/VotacaoServlet">Nova Votação</a>
			</td>
			<td>
				<a href="/SistemaVotacao/EleitorServlet">Novo Eleitor</a>
			</td>
			<td>
				<a href="/SistemaVotacao/ListaEleitoresServlet">Lista Eleitores</a>
			</td>
		</c:if>
		<td>
			<a href="/SistemaVotacao/LogoutServlet">Sair</a>
		</td>
	</tr>
</table>

<h1>Sistema Votação</h1>