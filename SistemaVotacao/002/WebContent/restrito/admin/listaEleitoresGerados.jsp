<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Votações - Sistema Votação</title>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Lista Eleitores Gerados</h1>
		<h2>Imprima esta lista</h2>
		<h2>${msg}</h2>
	
		<table border="0" align="center" cellspacing="10" cellpadding="10">
			<tbody>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td valign="top" style="border-right: 1px dashed #000000;width: 0px;" rowspan="${fn:length(eleitores)*2+1}" ><span style="text-rotation: 90;">Corte aqui</span></td>
					<td></td>
					<td></td>
				</tr>
				<c:forEach var="eleitor" items="${eleitores}" >
					<tr>
						<td><b>Login</b></td>
						<td><b>Nome</b></td>
						<td><b>Data</b></td>
						<td><b>Login</b></td>
						<td><b>Senha</b></td>
					</tr>
					<tr>
						<td align="center" style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
							${eleitor.login}
						</td>
						<td width="25" style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
							__________________________
						</td>
						<td style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
							____/____/______
						</td>
						<td align="center"  style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
							${eleitor.login}
						</td>
						<td align="center"  style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;">
							${eleitor.senha}
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td><c:out value="Total de eleitores : ${fn:length(eleitores)}"></c:out></td>
				</tr>
			</tfoot>
		</table>	
	</body>
</html>