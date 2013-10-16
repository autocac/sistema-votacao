package votacao.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.dao.DaoFactory;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

/**
 * Servlet implementation class GetImageServlet
 */
public class GetImageServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		String idVotacaoStr = request.getParameter("idVotacao");
		String tipo = request.getParameter("tipo");
		int idVotacao = Integer.parseInt(idVotacaoStr);
		
		byte[] imagem = null;
		if ("c".equals(tipo)) {//candidato
			String idCandidatoStr = request.getParameter("idCandidato");
			int idCandidato = Integer.parseInt(idCandidatoStr);
			CandidatoDao dao = DaoFactory.getInstance().getCandidatoDao();
			
			Candidato candidato = dao.buscarPorId(idVotacao, idCandidato);
			
			response.setContentType(candidato.getImageContentType());
			imagem = candidato.getImagem();
		} else if ("v".equals(tipo)) {//votacao
			VotacaoDao dao = DaoFactory.getInstance().getVotacaoDao();
			Votacao votacao = dao.buscarPorId(idVotacao);
			imagem = votacao.getFundo().getBytes();
		}
		
        response.setHeader("Content-Disposition","inline");
        
        try {
			
			if (imagem != null) {
				response.getOutputStream().write(imagem);
		        response.flushBuffer();
			}
        } catch (IOException e) {
			throw new BaseException(e);
		}   
	}
}
