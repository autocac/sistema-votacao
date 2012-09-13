package votacao.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Comparecimento;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.dao.ComparecimentoDao;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

/**
 * Servlet implementation class VotacaoServlet
 */
public class VotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see ServletBase#ServletBase()
     */
    public VotacaoServlet() {
        super();
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			int idVotacao = Integer.parseInt(request.getParameter("idVotacao"));
			int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
			int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
			
			CandidatoDao candidatoDao = new CandidatoDao();
			UsuarioDao usuarioDao = new UsuarioDao();
			VotacaoDao votacaoDao = new VotacaoDao();
			
			Usuario usuario = usuarioDao.buscarPorId(idUsuario);
			Votacao votacao = votacaoDao.buscarPorId(idVotacao);
			
			Candidato candidato = candidatoDao.buscarPorId(idVotacao, idCandidato);
			
			
			if (candidato != null) {
				candidato.receberVoto();
			}
			
			ComparecimentoDao comparecimentoDao = new ComparecimentoDao();
			Comparecimento comparecimento = new Comparecimento();
			comparecimento.setIdCandidato(idCandidato);
			comparecimento.setIdVotacao(idVotacao);
			comparecimento.setIdUsuario(idUsuario);
			
			comparecimentoDao.salvar(comparecimento);
			
			String nextJSP = "detalheVotacao.jsp";
			request.setAttribute("votacao", votacao);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}
}
