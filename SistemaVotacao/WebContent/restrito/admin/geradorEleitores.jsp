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
		<title>Geração de Eleitores - Sistema Votação</title>
		<script language="JavaScript" type="text/javascript">
			var clicado = 0;
			
			String.prototype.trim = function () {
			    return this.replace(/^\s*/, "").replace(/\s*$/, "");
			}
			
			function addTurma() {
   				var nomeTurma = document.getElementById("nomeTurma").value.trim();
   				
   				if (!isIdentificador(document.getElementById("nomeTurma"), "Nome Turma")) {
   					return false;
   				}

   				var numeroAlunos = parseInt(document.getElementById("numeroAlunos").value);
   				//Não eh loucura!!!
   				//Serve para testar se numeroAlunos eh NaN - Not A Number(tantan?)
   				if (numeroAlunos <= 0 || !(numeroAlunos < 0 || numeroAlunos > 0 || numeroAlunos == 0 ) ) {
   					alert('Numero de alunos inválido');
   					document.getElementById("numeroAlunos").focus();
   					return false;
   				}
   				var item = nomeTurma + " - " + numeroAlunos;
   				var listaTurmas = document.getElementById("listaTurmas");
   				
   				var i = listaTurmas.options.length - 1;
   				for (;i >= 0 && listaTurmas.options[i].value.substring(0, nomeTurma.length) != nomeTurma ;i--) {
   				}
   				
   				if (i >= 0) {
   					alert('Esta turma já está presente na lista');
   					return false;
   				}

   				try {
   					var newOpt = document.createElement('option');
   					newOpt.text = item;
   					newOpt.value = item;
   					listaTurmas.add(newOpt, null);
   				} catch (ex) {
   					listaTurmas.add(newOpt); //IE only
   				}
   				sortSelectByValue(listaTurmas);
				return false;
			}
			
			function isIdentificador(campo, nomeCampo) {
				var lista = ' -!@#$%&*(){}[]?="\'><,.';
				var indice = buscaCaracterLista(campo.value.trim(), lista);
				if (indice >= 0) {
					if (lista.charAt(indice) == ' ') {
						alert(nomeCampo + ' não pode ter caracter em branco');
					} else {
						alert(nomeCampo + ' não pode ter caracter "' + lista.charAt(indice) + '"');
					}
					
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
			
			function buscaCaracterLista(str, listaCaracteres) {
				var i = listaCaracteres.length;
				for (;i >= 0 && !contains(str, listaCaracteres.charAt(i));i--) {
				}
				return i;
			}
			
			function contains(str, caracter) {
				var ret = false;
				var i = 0;
				for (;i < str.length && !ret; i++) {
					ret = ret || (str.charAt(i) == caracter);
				}
				return ret;
			}
			
			function sortSelectByValue(obj){
			    var o = new Array();
			    for (var i=0; i<obj.options.length; i++){
			        o[o.length] = 
			        	new Option(
			        			obj.options[i].text, 
			        			obj.options[i].value, 
			        			obj.options[i].defaultSelected, 
			        			obj.options[i].selected);
			    }
			    o = o.sort(
			        function(a,b){ 
			            if ((a.value+"") < (b.value+"")) { return -1; }
			            if ((a.value+"") > (b.value+"")) { return 1; }
			            return 0;
			        } 
			    );

			    for (var i=0; i<o.length; i++){
			        obj.options[i] = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
			    }
			}
			
			function removerTurma() {
				var listaTurmas = document.getElementById("listaTurmas");
   				var i = listaTurmas.options.length - 1;
   				for (;i >= 0 ;i--) {
   					if (listaTurmas.options[i].selected) {
   						listaTurmas.remove(i);
   					}
   				}
				return false;
			}
			
			function gerarEleitores() {
				if (clicado == 0) {
					clicado = 1;
					var listaTurmas = document.getElementById("listaTurmas");
					var turmas = document.getElementById("turmas");
					var lista = '';
					var i = listaTurmas.options.length - 1;
	   				for (;i >= 0 ;i--) {
	   					lista = lista + listaTurmas.options[i].value + ';';
	   				}
	   				turmas.value = lista;
	   				document.frmGeracaoEleitores.submit();
            	} else {
					alert('Aguarde, operação em processamento');
				}
				return false;
			}
		</script>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Geração de Eleitores</h1>
		<h2>${msg}</h2>
	
		<form id="frmGeracaoEleitores" name="frmGeracaoEleitores" action="/SistemaVotacao/GeradorEleitoresServlet" method="post" >
			<table style="border-collapse:collapse;border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000; border-bottom: 3px solid #000000;" align="center">
				<tbody>
					<tr>
						<td>
							Nome Turma : 
						</td>
						<td>
							<input type="text" id="nomeTurma" name="nomeTurma" />
						</td>
						<td>
							<input type="button" id="btnAddTurma" name="btnAddTurma" value="Adicionar" onclick="javascript:addTurma();" />
						</td>
					</tr>
					<tr>
						<td>
							Número de alunos : 
						</td>
						<td colspan="2">
							<input type="text" id="numeroAlunos" name="numeroAlunos" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<select id="listaTurmas" name="listaTurmas" size="10" style="width: 200px;" >
							</select>
						</td>
						<td>
							<input type="button" id="btnRemoverTurma" name="btnRemoverTurma" value="Remover" onclick="javascript:removerTurma();" />
						</td>
					</tr>
					
					<tr>
						<td colspan="3">
							Obrigatório a alteração de senha : <input type="checkbox" id="chkAlteracaoObrigatoria" name="chkAlteracaoObrigatoria" />
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td>
							<input type="hidden" id="turmas" name="turmas"/>
						</td>
						<td>
						</td>
						<td>
							<input type="hidden" id="acao" name="acao" value="criar" />
							<input type="button" id="btnGerar" name="btnGerar" value="Gerar Eleitores" onclick="javascript:gerarEleitores();" />
						</td>
					</tr>
				</tfoot>
			</table>	
		</form>
	</body>
</html>