package votacao.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

/**
 * Servlet implementation class ResultadosServlet
 */
public class ResultadosServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if (user.getTipo() == Usuario.Tipo.ELEITOR) {
			throw new AreaRestritaException("Area restrita aos administradores");
		}
		
		VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();
		List<Votacao> votacoes = votacaoDao.buscarTodas();
		try {
			String nextJSP = "/restrito/admin/resultados.jsp";
			request.setAttribute("votacoes", votacoes);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}
}
