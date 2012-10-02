package votacao.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Comparecimento;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.dao.ComparecimentoDao;
import votacao.dao.DaoFactory;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;
import votacao.exception.DaoException;
import votacao.exception.JaVotouException;

/**
 * Servlet implementation class ConclusaoVotacaoServlet
 */
public class ConclusaoVotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see ServletBase#ServletBase()
     */
    public ConclusaoVotacaoServlet() {
        super();
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			int idVotacao = Integer.parseInt(request.getParameter("idVotacao"));
			int idCandidato = Integer.parseInt(request.getParameter("idCandidato"));
			
			CandidatoDao candidatoDao = DaoFactory.getInstance().getCandidatoDao();
			VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
			
			Usuario usuario = (Usuario)request.getSession().getAttribute("user");
			Votacao votacao = votacaoDao.buscarPorId(idVotacao);

			ComparecimentoDao comparecimentoDao = 
				DaoFactory.getInstance().getComparecimentoDao();
			
			Comparecimento comparecimento = comparecimentoDao.buscarPorId(idVotacao, usuario.getLogin());
			
			if (comparecimento != null) {
				throw new JaVotouException("Este usuario ja votou nesta votação");
			}
			
			synchronized(this) { 
				Candidato candidato = candidatoDao.buscarPorId(idVotacao, idCandidato);
				
				if (candidato == null) {
					throw new BaseException("Candidato nao encontrado >> idVotacao=" + idVotacao + "; idCandidato=" + idCandidato);
				}
				
				candidato.receberVoto();
				
				candidatoDao.salvar(candidato);
			}

			marcarComparecimento(
					idVotacao, 
					idCandidato, 
					usuario,
					comparecimentoDao);
			
			if (usuario.getTipo() == Usuario.Tipo.ELEITOR) {
				//chutando usuario apos votar
				request.getSession().invalidate();
			}

			String nextJSP = "/restrito/eleitor/conclusaoVotacao.jsp";
			request.setAttribute("votacao", votacao);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

	private void marcarComparecimento(int idVotacao, int idCandidato,
			Usuario usuario, ComparecimentoDao comparecimentoDao)
			throws DaoException {
		Comparecimento comparecimento;
		comparecimento = new Comparecimento();
		comparecimento.setIdCandidato(idCandidato);
		comparecimento.setIdVotacao(idVotacao);
		comparecimento.setLoginUsuario(usuario.getLogin());

		comparecimentoDao.salvar(comparecimento);
	}

}
