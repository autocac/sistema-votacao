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
		<title>Votações - Sistema Votação</title>
		<script language="JavaScript" type="text/javascript">
			var clicado = 0;
		
			String.prototype.trim = function () {
			    return this.replace(/^\s*/, "").replace(/\s*$/, "");
			}
			
			Array.prototype.remove = function(start, end) {
			    this.splice(start, end);
			    return this;
			}

			Array.prototype.insert = function(pos, item) {
			    this.splice(pos, 0, item);
			    return this;
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
				//mostrarSelecionados();
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
            
            function mostrarSelecionados() {
            	var listaParticipantes = document.getElementById('listaParticipantes');
            	var i = 0;
            	for (; i < listaParticipantes.length ; i++) {
            		alert(listaParticipantes.options[i].value);
            	}
            }
            
            function salvar() {
            	if (clicado == 0) {
            		clicado = 1;
            		
            		//mostrarSelecionados();
            		
            		var txtDescricao = document.getElementById('txtDescricao');
            		var numDiasEncerramento = document.getElementById('numDiasEncerramento').value;
            		var listaParticipantes = document.getElementById('listaParticipantes');
            		var participantes = document.getElementById('participantes');
					
            		var i = 0;
            		var listPipe = '';
            		for (; i < listaParticipantes.length ; i++) {
            			listPipe = 
            				listPipe +
            				listaParticipantes.options[i].value + ';';
            		}
            		participantes.value = listPipe;
            		//alert('participantes.value=' + participantes.value);

            		if (isEmpty(txtDescricao, 'Descrição votação')) {
						return false;
					}
            		
//             		var txtLocalizacaoCand = document.getElementById('txtLocalizacaoCand');
//             		if (isEmpty(txtLocalizacaoCand, 'Localização')) {
// 						return false;
// 					}
            		document.frmVotacao.submit();
            		
            		
            	} else {
					alert('Aguarde, operação em processamento');
				}
            	return false;
            }
            
            function cancelar() {
            	if (clicado == 0) {
            		clicado = 1;            		
            		
            		document.frmVotacao.action = "/SistemaVotacao/ListaVotacoesServlet";
            		document.frmVotacao.submit();
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
			
			function addCand() {
				cloneRow();
			}
			
            function cloneRow() {
            	  var tblBody = document.getElementById("candTable").tBodies[0];
            	  var newNode = tblBody.rows[0].cloneNode(true);
            	  tblBody.appendChild(newNode);
                  return false;
            }
            
            function deleteRows() {
            	var tblBody = document.getElementById("candTable").tBodies[0];
            	var i = tblBody.rows.length - 1;
            	for (; i > 0; i--) {
            		tblBody.deleteRow(i);
            	}
            	return false;
            }

		</script>
	</head>
	<body>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Votação</h1>
		<h2>${msg}</h2>
		
		<c:choose>
			<c:when test="${acao eq 'concluir'}">
				<table align="center">
					<tr>
						<td>
							<h3 align="left">Número votação : ${votacao.id}</h3>
							<h3 align="left">Descrição votação : ${votacao.descricao}</h3>
							<h3 align="left">
								Início da votação : <fmt:formatDate value="${votacao.periodo.dataInicio}" pattern="dd/MM/yyyy HH:mm:ss"/>
							</h3>
							<h3 align="left">
								Término da votação : <fmt:formatDate value="${votacao.periodo.dataFim}" pattern="dd/MM/yyyy HH:mm:ss"/>
							</h3>
							<h4 align="left">Lista de candidatos</h4>
							<ul>
								<c:forEach var="candidato" items="${candidatos}">
									<li>${candidato.titulo}</li>
								</c:forEach>
							</ul>						
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<form id="frmVotacao" name="frmVotacao" action="/SistemaVotacao/VotacaoServlet" method="post"  enctype="multipart/form-data" >
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
								
								<div id="divVotacao" style="display: block; width: 700px; height: 250px;"  >
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
								<div id="divEleitores" style="display: none; width: 700px; height: 250px;" >
									
									<table >
										<tr>
											<td>Eleitores</td>
											<td></td>
											<td>Participantes</td>
										</tr>
										<tr>
											<td rowspan="4">
												<select id="listaEleitores" name="listaEleitores" size="10" style="width: 200px;" multiple="multiple" title="Eleitores" >
													<c:forEach var="eleitor" items="${eleitores}">
														<option value="${eleitor.login}" >${eleitor.login} - ${eleitor.nome}</option>
													</c:forEach>
												</select>
												<input type="hidden" id="participantes" name="participantes" value="criar"/>
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
								<div id="divCandidatos" style="display: none; width: 700px; height: 250px; overflow: auto;" align="center" >
									<table id="candTable">
										<thead>
											<tr>
												<th>Título</th>
												<th>Descrição</th>
												<th>Imagem</th>
											</tr>
										</thead>
										<tbody>
											<tr id="rowToClone" >
												<td valign="top">
													<input type="text" id="txtTitulo" name="txtTitulo" size="20" />
												</td>
												<td>
													<textarea rows="4" cols="15" id="txtDescricaoCandidato" name="txtDescricaoCandidato" ></textarea>
												</td>
												<td>
													<input type="file" id="btnUpload" name="btnUpload" value="Upload" />
												</td>
											</tr>
										</tbody>
									</table>
									<input type="button" id="btnAddCandidato" name="btnAddCandidato" value="Adicionar" onclick="javascript:addCand();" />
									<input type="button" id="btnReset" name="btnReset" value="Reset" onclick="javascript:deleteRows();" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="right" style="border-collapse:collapse;border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000; border-bottom: 3px solid #000000; " >
								<input type="hidden" id="acao" name="acao" value="criar"/>
								<input type="button" id="btnCancelar" name="btnCancelar" value="Cancelar" onclick="javascript:cancelar();" />
								<input type="button" id="btnSalvar" name="btnSalvar" value="Salvar" onclick="javascript:salvar();" />
							</td>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</body>
</html>