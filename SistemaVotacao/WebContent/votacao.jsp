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
			
			function setBorder(id, px, att) {
				var value = px + 'px solid #000000';
				//alert(value);
				document.getElementById(id).style[att] = value;
			}
			
			function setBorders(id, px, esq, dir, cima, baixo) {
				if (esq) {
					setBorder(id, px, 'borderLeft');
				} else {
					setBorder(id, 0, 'borderLeft');
				}
				if (dir) {
					setBorder(id, px, 'borderRight');
				} else {
					setBorder(id, 0, 'borderRight');
				}
				if (cima) {
					setBorder(id, px, 'borderTop');
				} else {
					setBorder(id, 0, 'borderTop');
				}
				if (baixo) {
					setBorder(id, px, 'borderBottom');
				} else {
					setBorder(id, 0, 'borderBottom');
				}
			}
			
			function setDisplay(id, value) {
				document.getElementById(id).style.display = value;
			}
			
			
			
			function abrirAba(numeroAba) {
				if (numeroAba == 0) {
					setBorders('aba0', 3, true, true, true, false); 
					setBorders('aba1', 1, true, true, true, true); 
					setBorders('aba2', 1, true, true, true, true);
					
					setDisplay('divVotacao', 'block');
					setDisplay('divEleitores', 'none');
					setDisplay('divCandidatos', 'none');
				} else 	if (numeroAba == 1) {
					setBorders('aba0', 1, true, true, true, true); 
					setBorders('aba1', 3, true, true, true, false); 
					setBorders('aba2', 1, true, true, true, true);
					
					setDisplay('divVotacao', 'none');
					setDisplay('divEleitores', 'block');
					setDisplay('divCandidatos', 'none');
				} else 	if (numeroAba == 2) {
					setBorders('aba0', 1, true, true, true, true); 
					setBorders('aba1', 1, true, true, true, true); 
					setBorders('aba2', 3, true, true, true, false);
					
					setDisplay('divVotacao', 'none');
					setDisplay('divEleitores', 'none');
					setDisplay('divCandidatos', 'block');
				}
				return false;
			}

			function moverSelecionados(origem, destino, todos) {
				var i = origem.length - 1;
				for ( ; i >= 0; i--) {
					if (origem.options[i].selected || todos) {
						
						try {
							var newOpt = document.createElement('option');
							newOpt.text = origem.options[i].text;
							newOpt.value = origem.options[i].value;
							destino.add(newOpt, null);
						} catch (ex) {
							destino.add(newOpt); //IE only
						}
						
						origem.remove(i);
					}
				}
				sortSelectByValue(origem);
				sortSelectByValue(destino);
				return false;
			}
			
			function vai1() {
				
				moverSelecionados(
						document.getElementById('listaEleitores'), 
						document.getElementById('listaParticipantes'),
						false);
				return false;
			}
			
			function vaoTodos() {
				moverSelecionados(
						document.getElementById('listaEleitores'), 
						document.getElementById('listaParticipantes'),
						true);
				
				return false;
			}
			
			function volta1() {
				moverSelecionados(
						document.getElementById('listaParticipantes'),
						document.getElementById('listaEleitores'),
						false);
				
				return false;
			}
			
			function voltamTodos() {
				moverSelecionados(
						document.getElementById('listaParticipantes'),
						document.getElementById('listaEleitores'),
						true);
				
				return false;
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
			
			function addCand() {
				cloneRow();
			}
			
            function cloneRow() {
                    var row = document.getElementById("rowToClone"); // find row to copy
                    var table = document.getElementById("candTable"); // find table to append to
                    var clone = row.cloneNode(true); // copy children too
                    clone.id = "newID"; // change id or other attributes/contents
                    table.appendChild(clone); // add new row to end of table
                    
                    return false;
            }
		</script>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Votação</h1>
		<h2>${msg}</h2>
		
		<form id="frmVotacao" name="frmVotacao" action="/SistemaVotacao/UploadServlet" method="post" enctype="multipart/form-data"  >
		
			<table align="center" >
				<tr>
					<td id="aba0" style="border-collapse:collapse;border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000;" align="center" >
						<a href="#" onclick="javascript:abrirAba(0);">Votação</a>
					</td>
					<td id="aba1" style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;" align="center" >
						<a href="#" onclick="javascript:abrirAba(1);">Eleitores</a>
					</td>
					<td id="aba2" style="border-collapse:collapse;border-left: 1px solid #000000; border-top: 1px solid #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;" align="center" >
						<a href="#" onclick="javascript:abrirAba(2);">Candidatos</a>
					</td>
				</tr>
				<tr>
					<td colspan="3" style="border-collapse:collapse;border-left: 3px solid #000000; border-right: 2px solid #000000; border-bottom: 2px solid #000000;">
						
						<div id="divVotacao" style="display: block; width: 500px; height: 250px;"  >
							<table>
								<tr>
									<td>Descrição : </td>
									<td><input type="text" id="txtDescricao" name="txtDescricao" value="" /></td>
								</tr>
								<tr>
									<td>Encerramento em : </td>
									<td>
										<select id="numDiasEncerramento" name="numDiasEncerramento" title="Encerramento em :" >
											<option value="1" selected="selected" >1 dia</option>
											<option value="2" >2 dias</option>
											<option value="3" >3 dias</option>
											<option value="4" >4 dias</option>
											<option value="5" >5 dias</option>
											<option value="6" >6 dias</option>
											<option value="7" >7 dias</option>
											<option value="10" >10 dias</option>
											<option value="15" >15 dias</option>
											<option value="20" >20 dias</option>
											<option value="25" >25 dias</option>
											<option value="30" >30 dias</option>
											<option value="60" >60 dias</option>
											<option value="90" >90 dias</option>
										</select>
									</td>
								</tr>
							</table>
						</div>
						<div id="divEleitores" style="display: none; width: 500px; height: 250px;" >
							
							<table >
								<tr>
									<td>Eleitores</td>
									<td></td>
									<td>Participantes</td>
								</tr>
								<tr>
									<td rowspan="4">
										<select id="listaEleitores" name="listaEleitores" size="10" style="width: 200px;" multiple="multiple" title="Eleitores" >
											<option value="1" >Fulano da Silva Sauro1</option>
											<option value="2" >Fulano da Silva Sauro 2</option>
											<option value="3" >Fulano da Silva Sauro 3</option>
											<option value="4" >Fulano da Silva Sauro 4</option>
											<option value="5" >Fulano da Silva Sauro 5</option>
											<option value="6" >Fulano da Silva Sauro 6</option>
											<option value="7" >Fulano da Silva Sauro 7</option>
										</select>
									</td>
										
									<td align="center">
										<input type="button" id="btnVai1" onclick="javascript:vai1();" value=">"/>
									</td>
									<td rowspan="4">
										<select id="listaParticipantes" name="listaParticipantes" size="10"  style="width: 200px;" multiple="multiple" title="Participantes" >
										</select>
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="button" id="btnVaoTodos" onclick="javascript:vaoTodos();" value=">>"/>
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="button" id="btnVolta1" onclick="javascript:volta1();" value="<"/>
									</td>
								</tr>
								<tr>
									<td align="center">
										<input type="button" id="btnVoltamTodos"  onclick="javascript:voltamTodos();"  value="<<"/>
									</td>
								</tr>
							</table>
						</div>
						<div id="divCandidatos" style="display: none; width: 500px; height: 250px; overflow: auto;" >
							<table id="candTable" border="1">
								<tr>
									<td>
										<input type="radio" id="tipoCandidato" name="tipoCandidato" value="Texto" checked="checked" />Texto
										<input type="radio" id="tipoCandidato" name="tipoCandidato" value="Video" />Video
									</td>
								</tr>
								<tr id="rowToClone" >
									<td>
										Descrição : <input type="text" id="txtDescricaoCandidato" name="txtDescricaoCandidato" size="20" />
										<input type="button" id="btnAddCandidato" name="btnAddCandidato" value="Adicionar" onclick="javascript:addCand();" />
										<br />
										Arquivo : <input type="file" id="btnUpload" name="btnUpload" value="Upload" />
									</td>
								</tr>
							</table>
						</div>
						
					</td>
				</tr>
				<tr>
					<td colspan="3" align="right" style="border-collapse:collapse;border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000; border-bottom: 3px solid #000000; " >
						<input type="button" id="btnCancelar" name="btnCancelar" value="Cancelar" />
						<input type="submit" id="btnSalvar" name="btnSalvar" value="Salvar" />
					</td>
				</tr>
			</table>
	
		</form>
	</body>
</html>