package votacao.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Votacao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

/**
 * Servlet implementation class ListaCandidatosServlet
 */
public class DetalheVotacaoServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	/**
	 * @see ServletBase#ServletBase()
	 */
	public DetalheVotacaoServlet() {
		super();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			int idVotacao = Integer.parseInt(request.getParameter("idVotacao"));

			VotacaoDao votacaoDao = new VotacaoDao();

			Votacao votacao = votacaoDao.buscarPorId(idVotacao);

			String nextJSP = "/restrito/eleitor/detalheVotacao.jsp";
			request.setAttribute("votacao", votacao);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

}
