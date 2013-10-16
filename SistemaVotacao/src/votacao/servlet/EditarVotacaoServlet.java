package votacao.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Imagem;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;
import votacao.exception.DaoException;
import votacao.exception.UploadException;
import votacao.util.UploadManager;

/**
 * Servlet implementation class EditarVotacaoServlet
 */
public class EditarVotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;
       
	public enum Acao {
		ACRESCENTAR,
		MANTER,
		REMOVER
	}
	
    public EditarVotacaoServlet() {
        super();
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			UploadManager manager = new UploadManager(request);
			try {
				manager.uploadFormToMemory();
			} catch (UploadException e) {
				throw new BaseException(e);
			}
			
			
			String msg = "";
			
			String acao = request.getParameter("acao");
			if (acao == null) {
				acao = "listar";
			}
			if (manager.isCarregado()) {
				acao = manager.getString("acao");
			}

			
			if ("listar".equals(acao)) {
				VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
				List<Votacao> votacoes = votacaoDao.buscarTodas();
				request.setAttribute("votacoes", votacoes);
				String nextJSP = "/restrito/admin/listaVotacoesEditar.jsp";
				request.getRequestDispatcher(nextJSP).forward(request, response);
			} else if ("detalhe".equals(acao)) {
				int idVotacao = Integer.parseInt(request.getParameter("idVotacao"));
				mostrarDetalhe(request, response, idVotacao);
			} else if ("salvar".equals(acao)) {
				try {
					salvar(request, response, manager);
				} catch (VotacaoJaIniciadaException e) {
					int idVotacao = e.getVotacao().getId();
					msg = e.getMessage();
					request.setAttribute("msg", msg);
					mostrarDetalhe(request, response, idVotacao);
				}
			} else if ("apagar".equals(acao)) {
				int idVotacao = manager.getInt("idVotacao");
				apagarVotacao(idVotacao);
				msg = "Votação apagada com sucesso";
				
				VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
				List<Votacao> votacoes = votacaoDao.buscarTodas();
				request.setAttribute("votacoes", votacoes);
				request.setAttribute("msg", msg);
				String nextJSP = "/restrito/admin/listaVotacoesEditar.jsp";
				request.getRequestDispatcher(nextJSP).forward(request, response);
			}
			

		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

	private void apagarVotacao(int idVotacao) throws DaoException {
		VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
		Votacao votacao = votacaoDao.buscarPorId(idVotacao);
		
		votacaoDao.apagar(votacao.getId());
	}

	private void mostrarDetalhe(
			HttpServletRequest request,
			HttpServletResponse response,
			int idVotacao) throws DaoException,
			ServletException, IOException {
		
		VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
		Votacao votacao = votacaoDao.buscarPorId(idVotacao);
		request.setAttribute("votacao", votacao);
		
		UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
		List<Usuario> eleitores = usuarioDao.buscarTodosVotantes();
		eleitores.removeAll(votacao.getEleitorado());
		request.setAttribute("eleitores", eleitores);
		int random = (int)(Math.random()*999999999);
		request.setAttribute("random", String.format("%09d", random) );

		String nextJSP = "/restrito/admin/editarVotacao.jsp";
		request.getRequestDispatcher(nextJSP).forward(request, response);
	}

	

	private void salvar(HttpServletRequest request,
			HttpServletResponse response, UploadManager manager) throws VotacaoJaIniciadaException, BaseException {
		
		String msg = "";
				
		String descricao = manager.getString("txtDescricao");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		
		Date dataIni;
		Date dataFim;
		try {
			String dtIni = manager.getString("txtDataInicio");
			dataIni = sdf.parse(dtIni);
			String dtFim = manager.getString("txtDataFim");
			dataFim = sdf.parse(dtFim);
		} catch (ParseException e) {
			throw new BaseException(e);
		}
		
		Periodo periodo = new Periodo(dataIni, dataFim, "dd/MM/yyyy HH:mm:ss");
		
		String[] participantes = manager.getString("participantes").split(";");
		
		System.out.println("descricao=" + descricao);

		System.out.println("participantes=" + participantes);
		
		byte[] arrayImagemFundo = (byte[])manager.get("btnUploadImagemFundo");
		String imagemFundoFileContentType = (String)manager.get("btnUploadImagemFundo_content_type");
		String imagemFundoFileName = (String)manager.get("btnUploadImagemFundo_file");
		Imagem imgFundo = new Imagem(imagemFundoFileName, imagemFundoFileContentType, arrayImagemFundo);
		System.out.println(imgFundo);
		
		List idCandList = manager.getList("txtIdCand");
		List titulosList = manager.getList("txtTitulo");
		List descCandidatoList = manager.getList("txtDescricaoCandidato");
		List btnUploadFile = manager.getList("btnUpload_file");
		List btnUploadFileContentType = manager.getList("btnUpload_content_type");
		List btnUpload = manager.getList("btnUpload");
		
		VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
		int idVotacao = manager.getInt("idVotacao");
		Votacao votacao = votacaoDao.buscarPorId(idVotacao);
		
		verificaInicioVotacao(votacao);
		
		List<Candidato> candidatos = 
			getCandidatos(
					idCandList,
					titulosList,
					descCandidatoList, 
					btnUploadFile, 
					btnUpload,
					btnUploadFileContentType);
		CandidatoDao candidatoDao = DaoFactory.getInstance().getCandidatoDao();
		List<Candidato> candidatosAtuais = candidatoDao.buscarPorVotacao(idVotacao);
		
		Map<Acao, List<Candidato>> mapaAcoes = merge(candidatos, candidatosAtuais);
		votacao.getCandidatos().clear();
		votacao.getCandidatos().addAll(mapaAcoes.get(Acao.MANTER));
		votacao.getCandidatos().addAll(mapaAcoes.get(Acao.ACRESCENTAR));
		
		List<Usuario> eleitorado = getEleitorado(participantes);
		


		Usuario admin = (Usuario)request.getSession().getAttribute("user");
		
		votacao.setDescricao(descricao);
		votacao.setPeriodo(periodo);
		votacao.setAdministrador(admin);
		votacao.setCandidatos(candidatos);
		votacao.setEleitorado(eleitorado);
		votacao.setFundo(imgFundo);
		
		votacaoDao.salvar(votacao);
		
		msg = "Votação criada com sucesso";
		request.setAttribute("msg", msg);
		request.setAttribute("acao", "concluir");
		request.setAttribute("votacao", votacao);
		request.setAttribute("candidatos", candidatos);
		
		String nextJSP = "/restrito/admin/conclusaoNovaVotacao.jsp";
		try {
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (ServletException e) {
			throw new BaseException(e);
		} catch (IOException e) {
			throw new BaseException(e);
		}
	}

	private void verificaInicioVotacao(Votacao votacao)
			throws VotacaoJaIniciadaException {
		int totalVotos = getTotalVotos(votacao);
		if (totalVotos > 0) {
			throw new VotacaoJaIniciadaException(votacao, totalVotos);
		}
	}
		
	private int getTotalVotos(Votacao votacao) {
		int totalVotosDados = 0;
		for (Candidato c : votacao.getCandidatos()) {
			totalVotosDados = totalVotosDados + c.getNumeroVotos();
		}
		return totalVotosDados;
	}



	/**
	 * <p>
	 * 	Retorna um mapa com a seguinte estrutura:
	 * 	"ACRESCENTAR" - Lista dos candidatos a serem inseridos
	 *  "MANTER" - Lista dos candidatos que devem sofrer update
	 *  "REMOVER" - Lista dos candidatos que devem ser removidos
	 * </p>
	 * @param candidatos
	 * @param candidatosAtuais
	 * @return
	 */
	private Map<Acao, List<Candidato>> merge(
			List<Candidato> candidatos,
			List<Candidato> candidatosAtuais) {
		
		Map<Acao, List<Candidato>> acoes = new HashMap<Acao, List<Candidato>>();
		
		//Esta chamada separa os novos candidatos dos atuais
		//soh ficando na List candidatos os candidatos que ja estavam na base
		List<Candidato> novosCandidatos = getNovosCandidatosList(candidatos);
		//Adiciona candidatos a serem acrescentados
		acoes.put(Acao.ACRESCENTAR, novosCandidatos);
		
		//Gera mapa dos candidatos que foram informados pela pagina
		//que ja estavam na base
		Map<Integer, Candidato> mapCandPassadosQueJaEstavamNaBase = getMapByList(candidatos);
		//Gera mapa dos candidatos que estao atualmente na base
		Map<Integer, Candidato> mapCandAtuaisPersistidos = getMapByList(candidatosAtuais);
		
		//Gera conjunto de chaves dos candidatos informados pela pagina
		//que ja estavam na base
		Set<Integer> conjCandidatosMantidos = mapCandPassadosQueJaEstavamNaBase.keySet();
		//Gera conjunto de chaves dos candidatos que estao atualmente na base
		Set<Integer> conjCandidatosAtuais = mapCandAtuaisPersistidos.keySet();
		preencherImagemAtual(mapCandPassadosQueJaEstavamNaBase, mapCandAtuaisPersistidos);
		acoes.put(Acao.MANTER, candidatos);
		
		//Gera conjunto de chaves que contem os candidatos que deverao
		//ser removidos
		//Isso eh obtido pegando todos os candidatos atualmente na base
		//e removendo desse conjunto aqueles que serao mantidos
		conjCandidatosAtuais.removeAll(conjCandidatosMantidos);
		acoes.put(Acao.REMOVER, getListFromChaves(conjCandidatosAtuais, mapCandAtuaisPersistidos));
		
		return acoes;
	}

	private List<Candidato> getListFromChaves(
			Set<Integer> conjCandidatosAtuais, Map<Integer, Candidato> mapCandAtuaisPersistidos) {
		List<Candidato> removidosList = new ArrayList<Candidato>();
		for (Integer chaves : conjCandidatosAtuais) {
			removidosList.add(mapCandAtuaisPersistidos.get(chaves));
		}
		return removidosList;
	}

	/**
	 * <p>
	 * Preenche com a imagem atualmente na base se for o caso
	 * </p>
	 * @param mapCandPassadosQueJaEstavamNaBase
	 * @param mapCandAtuaisPersistidos
	 */
	private void preencherImagemAtual(
			Map<Integer, Candidato> mapCandPassadosQueJaEstavamNaBase,
			Map<Integer, Candidato> mapCandAtuaisPersistidos) {
		for (Candidato c : mapCandPassadosQueJaEstavamNaBase.values()) {
			//Se o candidato nao informou uma nova imagem copia aquela que ja esta presente
			if (c.getImagem() == null || c.getImagem().length < 3) {//
				c.setImagem(
						mapCandAtuaisPersistidos.get(c.getId()).getImagem());
				c.setImageContentType(
						mapCandAtuaisPersistidos.get(c.getId()).getImageContentType());
			}
		}
	}

	private List<Candidato> getNovosCandidatosList(List<Candidato> candidatos) {
		List<Candidato> novosCandidatosList = new ArrayList<Candidato>();
		Iterator<Candidato> it = novosCandidatosList.iterator();
		while (it.hasNext()) {
			Candidato candidato = it.next(); 
			if (candidato.getId() == 0) {
				novosCandidatosList.add(candidato);
				it.remove();//Remove os candidatos novos
			}
		}
		return novosCandidatosList;
	}

	private Map<Integer, Candidato> getMapByList(List<Candidato> candidatos) {
		Map<Integer, Candidato> mapFromList = new HashMap<Integer, Candidato>();
		for (Candidato candidato : candidatos) {
			mapFromList.put(candidato.getId(), candidato);
		}
		return mapFromList;
	}

	/**
	 * @param participantes
	 * @return
	 * @throws DaoException 
	 */
	private List<Usuario> getEleitorado(String[] participantes) throws DaoException {
		List<Usuario> eleitorado = new ArrayList<Usuario>();
		UsuarioDao dao = DaoFactory.getInstance().getUsuarioDao();
		for (String login : participantes) {
			Usuario user = dao.buscarPorLogin(login);
			eleitorado.add(user);
		}
		return eleitorado;
	}

	/**
	 * <p>
	 * 	Retorna a lista de candidatos enviada pela pagina.
	 *  Os candidatos cujo id nao estao definidos sao novos.
	 *  Os candidatos cujos id's estao na tabela e nao estao na lista foram eliminados.
	 *  As imagens dos candidatos que continuam que:
	 *  1 - Sao nulas, devem ser mantidas
	 *  2 - Nao sao nulas, devem ser alteradas
	 *  
	 *  Considerando as circustancias acima, a lista retornada por este metodo
	 *  deve ser "mergeada" com a lista de candidatos da base.
	 * </p>
	 * @param titulosList
	 * @param descCandidatoList
	 * @param btnUploadFile
	 * @param btnUpload
	 * @param btnUploadFileContentType
	 * @return
	 */
	private List<Candidato> getCandidatos(
			List idCandList,
			List titulosList,
			List descCandidatoList, 
			List btnUploadFile, 
			List btnUpload,
			List btnUploadFileContentType) {
		List<Candidato> candidatos = new ArrayList<Candidato>();
		for (int i = 0;i < titulosList.size() ;i++) {
			String idCand = (String)idCandList.get(i);
			String titulo = (String)titulosList.get(i);
			String descCandidato = (String)descCandidatoList.get(i);
			String fileName = (String)btnUploadFile.get(i);
			System.out.println(fileName);
			byte[] arrayImagem = (byte[])btnUpload.get(i);
			String imageContentType = (String)btnUploadFileContentType.get(i);
			
			Candidato candidato = new Candidato();
			try {
				candidato.setId(Integer.parseInt(idCand));
			} catch (Exception e) {
			}
			candidato.setNome(titulo);
			candidato.setDescricao(descCandidato);
			candidato.setImagem(arrayImagem);
			candidato.setImageContentType(imageContentType);
			
			candidatos.add(candidato);
		}
		return candidatos;
	}
}
