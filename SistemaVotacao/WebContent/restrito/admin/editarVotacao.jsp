<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="votacao.bean.ComponenteData"%>
<%@page import="votacao.bean.Votacao"%>
<%@page import="votacao.bean.Periodo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%!
	
	public String montaComboComponente(String nomeData, ComponenteData c){
	
		StringBuffer out = new StringBuffer();
		
		out.append("<td>").append("\n");
		out.append("	<select id=\"cboComponente" + nomeData + c.getTipo().name() 
				+ "\" name=\"cboComponente" + c.getTipo().name() 
				+ "\">").append("\n");
		
		int min = c.getTipo().getMin();
		int max = c.getTipo().getMax();
		for(int i = min; i <= max; i++) {
			String selected = "";
			if (c.getValor() == i) {
				selected = "selected";
			}
			String valor = String.format("%0" + c.getTipo().getNumDigitos() + "d", i);
			out.append(
				"		<option " + 
				selected + " value=\"" + 
				valor +"\" >" + 
				valor +"</option>").append("\n");
		}
		
		
		out.append("	</select>");
		out.append("</td>");
		return out.toString();
	}

%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Editar Votação - Sistema Votação</title>
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
			
            function mostrarSelecionados() {
            	var listaParticipantes = document.getElementById('listaParticipantes');
            	var i = 0;
            	for (; i < listaParticipantes.length ; i++) {
            		alert(listaParticipantes.options[i].value);
            	}
            }
            
            function getData(nomeData) {
            	//alert('2');
            	var combo = "cboComponente" + nomeData;
            	var componentes = ["ANO", "MES", "DIA", "HORA", "MINUTO", "SEGUNDO"];
            	var i = 0;
            	var mapa = new Array();
            	//alert('3');
            	for (;i < componentes.length; i++) {
            		//alert('4>>' + componentes[i]);
            		
            		var nomeCombo = combo + componentes[i];
            		//alert('5>>' + nomeCombo);
            		mapa[componentes[i]] = document.getElementById(nomeCombo).value;
            	}
            	var dataStr = mapa["ANO"] + "-" + mapa["MES"] + "-" + mapa["DIA"] + "-" + mapa["HORA"] + "-" + mapa["MINUTO"] + "-" + mapa["SEGUNDO"];
            	//alert(nomeData + "=" + dataStr);
            	return dataStr;
            }
            
            function salvar() {
            	if (clicado == 0) {
            		clicado = 1;
            		
            		//mostrarSelecionados();
            		//alert('1');
            		var txtDescricao = document.getElementById('txtDescricao');
            		var dataInicio = getData('DataInicio');
            		document.getElementById('txtDataInicio').value = dataInicio;
            		var dataFim = getData('DataFim');
            		document.getElementById('txtDataFim').value = dataFim;
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
            
            function apagar() {
            	alert('Nao implementado ainda');
            }
            
            function apagarCandidato(name) {
            	//alert('name=' + name);
            	var tblBody = document.getElementById("candTable").tBodies[0];
            	var i = tblBody.rows.length - 1; //parseInt(document.getElementById("proxIdCand").value);
            	//alert('i = ' + i);
            	//alert('tblBody.rows.length=' + tblBody.rows.length);
            	
            	for (; i >= 0; i--) {
            		//alert('tblBody.rows[' + i + '].cells.length=' + tblBody.rows[i].cells.length);
            		//alert('tblBody.rows[' + i + '].cells[0].id=' + tblBody.rows[i].cells[0].id);
            		var chaveDeBusca = getElementById(tblBody.rows[i], "chaveDeBusca");
            		//if (tblBody.rows[i].cells[0].id == name) {
            		//alert("chaveDeBusca.value=" + chaveDeBusca.value);
            		if (chaveDeBusca.value == name) {
            			tblBody.deleteRow(i);
            		}
            	}
            	return false;
            }
            
			function addCand() {
				cloneRow();
			}
			
            function cloneRow() {
				var row = document.getElementById("rowToClone"); // find row to copy
                var table = document.getElementById("candTable"); // find table to append to
                var clone = row.cloneNode(true); // copy children too
                clone.id = "rowToClone"; // change id or other attributes/contents
                var txtId = getElementById(clone, 'txtIdCand');
                //alert('txtId=' + txtId.id);
              	var txtProxIdCand = document.getElementById("proxIdCand");
              	var proxIdCand = parseInt(txtProxIdCand.value);
              	txtId.value = 0;//Id zero vai indicar que eh um acrescimo de candidato
              	
              	txtProxIdCand.value = proxIdCand + 1; 
                
              	var tdCandId = getElementById(clone, "chaveDeBusca");//Busca pelo id do original
              	var identificador = "cand" + proxIdCand;
              	tdCandId.value = identificador;//Altera id
              	
              	var img = getElementById(clone, 'imagem');
              	img.src = '';
              	
              	
              	var tit = getElementById(clone, 'txtTitulo');
              	tit.value = '';
              	
              	var desc = getElementById(clone, 'txtDescricaoCandidato');
              	desc.value = '';
              	
              	var btnApagar = getElementById(clone, 'btnApagarCand');
              	btnApagar.onclick= function() {
              		apagarCandidato(identificador);
              	};
              	btnApagar.value = 'Apagar';
              	
              	table.tBodies[0].appendChild(clone); // add new row to end of table
                    
                return false;
            }
            
            function getElementById(obj, id) {
            	var i = 0;
            	for (;i < obj.children.length; i++) {
            		var child = obj.children[i];
            		if (child.id == id) {
            			return child;
            		}
            	}
            	
            	for (i = 0; i < obj.children.length; i++) {
            		var child = getElementById(obj.children[i], id);
            		if (child != null) {
            			return child;
            		}
            	}
            	return null;
            }

		</script>

	</head>
	<body>
		<%
			Votacao votacao = (Votacao)request.getAttribute("votacao");
			Periodo p = votacao.getPeriodo();
		%>
		<jsp:include page="/menu.jsp" />

		<h1 align="center">Votação</h1>
		<h2>${msg}</h2>
		
		<c:choose>
<%-- 			<c:when test="${acao eq 'concluir'}"> --%>
			<c:when test="${fn:contains(acao, 'concluir')}">
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
				<form id="frmVotacao" name="frmVotacao" action="/SistemaVotacao/EditarVotacaoServlet" method="post"  enctype="multipart/form-data" >
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
											<td>ID VOTAÇÃO : ${votacao.id}</td>
											<td><input type="hidden" id="idVotacao" name="idVotacao" value="${votacao.id}"/> </td>
										</tr>
										<tr>
											<td>Descrição : </td>
											<td><input type="text" id="txtDescricao" name="txtDescricao" value="${votacao.descricao}" /></td>
										</tr>
										<tr>
											<td>Início em : </td>
											<td>
												<table>
													<tr>
														<td>Ano</td>
														<td>Mês</td>
														<td>Dia</td>
														<td>Hora</td>
														<td>Minuto</td>
														<td>Segundo</td>
													</tr>
													<%
														for(ComponenteData c : p.getComponentesDataInicio()) {
															out.print(montaComboComponente("DataInicio", c));
														}
													%>
													<tr>
													</tr>
												</table>
												<input type="hidden" id="txtDataInicio" name="txtDataInicio"/>
											</td>
										</tr>
										<tr>
											<td>Encerramento em : </td>
											<td>
												<table>
													<tr>
														<td>Ano</td>
														<td>Mês</td>
														<td>Dia</td>
														<td>Hora</td>
														<td>Minuto</td>
														<td>Segundo</td>
													</tr>
													<%
														for(ComponenteData c : p.getComponentesDataFim()) {
															out.print(montaComboComponente("DataFim", c));
														}
													%>
													<tr>
													</tr>
												</table>
												<input type="hidden" id="txtDataFim" name="txtDataFim"/>
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
													<c:forEach var="eleitor" items="${votacao.eleitorado}">
														<option value="${eleitor.login}" >${eleitor.login} - ${eleitor.nome}</option>
													</c:forEach>
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
											
											<c:forEach var="candidato" items="${votacao.candidatos}" varStatus="loopCounter" >
												<tr id="rowToClone" >
													<td valign="middle" id="cand${candidato.id}">
														<input type="hidden" id="chaveDeBusca" name="chaveDeBusca" value="cand${candidato.id}"/>
														<input type="button" id="btnApagarCand" name="btnApagarCand" value="Apagar" onclick="javascript:apagarCandidato('cand${candidato.id}');"/>
														<input type="hidden" id="txtIdCand" name="txtIdCand" value="${candidato.id}"/>
														<input type="text" id="txtTitulo" name="txtTitulo" size="20" value="${candidato.nome}"/>
													</td>
													<td valign="middle">
														<textarea rows="4" cols="15" id="txtDescricaoCandidato" name="txtDescricaoCandidato" ><c:out value="${candidato.descricao}"/></textarea>
													</td>
													<td valign="middle">
														<img id="imagem" name="imagem" src="/SistemaVotacao/GetImageServlet?idVotacao=${votacao.id}&idCandidato=${candidato.id}&random=${random}" width="100" height="80" />
														<input type="file" id="btnUpload" name="btnUpload" value="Upload" />
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<input type="button" id="btnAddCandidato" name="btnAddCandidato" value="Adicionar" onclick="javascript:addCand();" />
									<input type="button" id="btnReset" name="btnReset" value="Reset" onclick="javascript:deleteRows();" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="right" style="border-collapse:collapse;border-left: 3px solid #000000; border-top: 3px solid #000000; border-right: 3px solid #000000; border-bottom: 3px solid #000000; " >
								<table border="0" width="100%">
									<tr>
										<td colspan="1" align="left">
											<input type="button" id="btnApagar" name="btnApagar" value="Apagar" onclick="javascript:apagar();" />
										</td>
										<td colspan="2" align="right">

											<input type="hidden" id="proxIdCand" name="proxIdCand" value="${votacao.maxIdCandidato + 1}"/>
											<input type="hidden" id="acao" name="acao" value="salvar"/>
											<input type="button" id="btnCancelar" name="btnCancelar" value="Cancelar" onclick="javascript:cancelar();" />
											<input type="button" id="btnSalvar" name="btnSalvar" value="Salvar" onclick="javascript:salvar();" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</body>
</html>