package votacao.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			
			CandidatoDao candidatoDao = new CandidatoDao();
			VotacaoDao votacaoDao = new VotacaoDao();
			
			Usuario usuario = (Usuario)request.getSession().getAttribute("user");
			Votacao votacao = votacaoDao.buscarPorId(idVotacao);
			
			Candidato candidato = candidatoDao.buscarPorId(idVotacao, idCandidato);
			
			
			if (candidato == null) {
				throw new BaseException("Candidato nao encontrado >> idVotacao=" + idVotacao + "; idCandidato=" + idCandidato);
			}

			ComparecimentoDao comparecimentoDao = new ComparecimentoDao();
			Comparecimento comparecimento = new Comparecimento();
			comparecimento.setIdCandidato(idCandidato);
			comparecimento.setIdVotacao(idVotacao);
			comparecimento.setLoginUsuario(usuario.getLogin());
			
			comparecimentoDao.salvar(comparecimento);
			
			if (usuario.getTipo() == Usuario.Tipo.ELEITOR) {
				//chutando usuario após votar
				request.getSession().invalidate();
			}
			
			candidato.receberVoto();

			String nextJSP = "/restrito/eleitor/conclusaoVotacao.jsp";
			request.setAttribute("votacao", votacao);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}
}
