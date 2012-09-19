package votacao.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.VideoCandidato;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

/**
 * Servlet implementation class VotacaoServlet
 */
public class VotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		try {
			String msg = "";
			String acao = request.getParameter("acao");
			
			if ("criar".equals(acao) || "salvar".equals(acao)) {
				String descricao = request.getParameter("txtDescricao");
				int numDiasEncerramento = Integer.parseInt(request.getParameter("numDiasEncerramento"));
				String participantes = request.getParameter("participantes");
				String localizacaoCand = request.getParameter("txtLocalizacaoCand");
				
				System.out.println("descricao=" + descricao);
				System.out.println("numDiasEncerramento=" + numDiasEncerramento);
				System.out.println("participantes=" + participantes);
				System.out.println("localizacaoCand=" + localizacaoCand);
				
				List<Candidato> candidatos = getCandidatos(localizacaoCand);
				List<Usuario> eleitores = getEleitores(participantes);
				
				
				Usuario admin = (Usuario)request.getSession().getAttribute("user");
				
				Votacao votacao = new Votacao();
				votacao.setDescricao(descricao);
				votacao.setPeriodo(getPeriodo(numDiasEncerramento));
				votacao.setAdministrador(admin);
				votacao.setCandidatos(candidatos);
				votacao.setEleitorado(eleitores);
				
				VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
				votacaoDao.criar(votacao);
				
				generateAsx(votacao);
				
				msg = "Votação criada com sucesso";
				request.setAttribute("msg", msg);
				request.setAttribute("acao", "concluir");
				request.setAttribute("votacao", votacao);
				request.setAttribute("candidatos", candidatos);
				
			} else {
				UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
				List<Usuario> eleitores = usuarioDao.buscarTodosVotantes();
				
				request.setAttribute("eleitores", eleitores);
				request.setAttribute("acao", "criar");
			}
			
	
			String nextJSP = "/restrito/admin/votacao.jsp";
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}		
	}

	private void generateAsx(Votacao votacao) throws BaseException {
		for (Candidato cand : votacao.getCandidatos()) {
			VideoCandidato video = (VideoCandidato)cand;
			createAsxFile(video.getArquivo().getParentFile(), video);
		}
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

	private List<Usuario> getEleitores(String participantes) {
		String[] arrayParticipantes = participantes.split(";");
		List<Usuario> eleitores = new ArrayList<Usuario>();
		UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
		for (String loginEleitor : arrayParticipantes) {
			loginEleitor = loginEleitor.trim();
			Usuario eleitor = usuarioDao.buscarPorLogin(loginEleitor);
			if (eleitor != null) {
				eleitores.add(eleitor);
			} else {
				System.out.println("login = " + loginEleitor + " não encontrado");
			}
		}
		return eleitores;
	}

	private List<Candidato> getCandidatos(String localizacaoCand) throws BaseException {
		File dirCandidatos = new File(localizacaoCand);
		if (!dirCandidatos.isDirectory()) {
			throw new BaseException("Diretório de candidatos inválido");
		}
		String[] fileList = dirCandidatos.list();
		List<Candidato> candidatos = new ArrayList<Candidato>();
		for (String fileNameCand : fileList) {
			File candFile = new File(dirCandidatos, fileNameCand);
			VideoCandidato cand = new VideoCandidato();
			cand.setDescricao(candFile.getName());
			cand.setArquivo(candFile);
			cand.setTitulo(candFile.getAbsolutePath());
			
			
			
			candidatos.add(cand);
		}	
		return candidatos;
	}

	private void createAsxFile(File dirCandidatos, VideoCandidato cand) throws BaseException {
		File asxFile = new File(dirCandidatos, cand.getId() + ".asx");
		try {
			RandomAccessFile raf = new RandomAccessFile(asxFile, "rw");
			raf.writeUTF("<ASX Version=\"3.0\"><entry><ref href=\"" + cand.getArquivo().getAbsolutePath() + "\" /></entry></ASX>");
			raf.close();
			cand.setArquivoAsx(asxFile);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

}
