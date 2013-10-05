<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Login - Sistema Votação</title>
	</head>
	<body>
		<h1>Upload - Sistema Votação</h1>
		<h2>${msg}</h2>
		<form id="frmUpload" name="frmUpload" action="UploadServlet" method="post" enctype="multipart/form-data"  >
			<table>
				<tr>
					<td>Arquivo : </td>
					<td>
						<input type="file" id="uploadFile" name="uploadFile" size="15"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="submit" id="btnUpload" name="btnUpload" value="Upload"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>