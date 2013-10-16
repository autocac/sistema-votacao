package votacao.servlet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Imagem;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;
import votacao.exception.DaoException;
import votacao.exception.UploadException;
import votacao.util.UploadManager;

/**
 * Servlet implementation class VotacaoServlet
 */
public class VotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

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
			
			if (manager.isCarregado() && 
					("criar".equals(manager.getString("acao")) || 
							"salvar".equals(manager.getString("acao")))) {
				
				String descricao = manager.getString("txtDescricao");
				int numDiasEncerramento = manager.getInt("numDiasEncerramento");
				String[] participantes = manager.getString("participantes").split(";");
				
				System.out.println("descricao=" + descricao);
				System.out.println("numDiasEncerramento=" + numDiasEncerramento);
				System.out.println("participantes=" + participantes);
				
				byte[] arrayImagemFundo = (byte[])manager.get("btnUploadImagemFundo");
				String imagemFundoFileContentType = (String)manager.get("btnUploadImagemFundo_content_type");
				String imagemFundoFileName = (String)manager.get("btnUploadImagemFundo_file");
				Imagem imgFundo = new Imagem(imagemFundoFileName, imagemFundoFileContentType, arrayImagemFundo);
				System.out.println(imgFundo);
				
				List titulosList = manager.getList("txtTitulo");
				List descCandidatoList = manager.getList("txtDescricaoCandidato");
				List btnUploadFile = manager.getList("btnUpload_file");
				List btnUploadFileContentType = manager.getList("btnUpload_content_type");
				List btnUpload = manager.getList("btnUpload");
				
				
				List<Candidato> candidatos = 
					getCandidatos(
							titulosList,
							descCandidatoList, 
							btnUploadFile, 
							btnUpload,
							btnUploadFileContentType);
				
				List<Usuario> eleitorado = getEleitorado(participantes);
				
				Votacao votacao = new Votacao();

				Usuario admin = (Usuario)request.getSession().getAttribute("user");
				
				votacao.setDescricao(descricao);
				votacao.setPeriodo(getPeriodo(numDiasEncerramento));
				votacao.setAdministrador(admin);
				votacao.setCandidatos(candidatos);
				votacao.setEleitorado(eleitorado);
				votacao.setFundo(imgFundo);
				
				VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
				votacaoDao.criar(votacao);
				
				msg = "Votação criada com sucesso";
				request.setAttribute("msg", msg);
				request.setAttribute("acao", "concluir");
				request.setAttribute("votacao", votacao);
				request.setAttribute("candidatos", candidatos);
				
				String nextJSP = "/restrito/admin/conclusaoNovaVotacao.jsp";
				request.getRequestDispatcher(nextJSP).forward(request, response);
				
			} else {
				UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
				List<Usuario> eleitores = usuarioDao.buscarTodosVotantes();
				
				request.setAttribute("eleitores", eleitores);
				request.setAttribute("acao", "criar");
				
				String nextJSP = "/restrito/admin/votacao.jsp";
				request.getRequestDispatcher(nextJSP).forward(request, response);
			}
		} catch (Exception e) {
			throw new BaseException(e); 
		}		
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
	 * 
	 * @param titulosList
	 * @param descCandidatoList
	 * @param btnUploadFile
	 * @param btnUpload
	 * @param btnUploadFileContentType
	 * @return
	 */
	private List<Candidato> getCandidatos(
			List titulosList,
			List descCandidatoList, 
			List btnUploadFile, 
			List btnUpload,
			List btnUploadFileContentType) {
		List<Candidato> candidatos = new ArrayList<Candidato>();
		for (int i = 0;i < titulosList.size() ;i++) {
			String titulo = (String)titulosList.get(i);
			String descCandidato = (String)descCandidatoList.get(i);
			String fileName = (String)btnUploadFile.get(i);
			System.out.println(fileName);
			byte[] arrayImagem = (byte[])btnUpload.get(i);
			String imageContentType = (String)btnUploadFileContentType.get(i);
			
			Candidato candidato = new Candidato();
			candidato.setNome(titulo);
			candidato.setDescricao(descCandidato);
			candidato.setImagem(arrayImagem);
			candidato.setImageContentType(imageContentType);
			
			candidatos.add(candidato);
		}
		return candidatos;
	}

	private Periodo getPeriodo(int numDiasEncerramento) {
		Calendar cal = Calendar.getInstance();
		Date agora = new Date();
		cal.setTime(agora);
		cal.set(Calendar.SECOND, 0);
		agora = new Date(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_MONTH, numDiasEncerramento);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 55);
		Date termino = new Date(cal.getTime().getTime());
		Periodo periodo = new Periodo(agora, termino, "dd/MM/yyyy HH:mm:ss");
		return periodo;
	}

}
