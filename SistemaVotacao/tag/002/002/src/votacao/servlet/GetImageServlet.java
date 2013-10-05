package votacao.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.dao.CandidatoDao;
import votacao.dao.DaoFactory;
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
		int idVotacao = Integer.parseInt(idVotacaoStr);
		String idCandidatoStr = request.getParameter("idCandidato");
		int idCandidato = Integer.parseInt(idCandidatoStr);
		CandidatoDao dao = DaoFactory.getInstance().getCandidatoDao();
		
		Candidato candidato = dao.buscarPorId(idVotacao, idCandidato);
		
		response.setContentType(candidato.getImageContentType());
        response.setHeader("Content-Disposition","inline");
        
        try {
			byte[] imagem = candidato.getImagem();
			if (imagem != null) {
				response.getOutputStream().write(imagem);
		        response.flushBuffer();
			}
        } catch (IOException e) {
			throw new BaseException(e);
		}   
	}
}
