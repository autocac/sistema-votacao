package votacao.servlet;

import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;


/**
 * Servlet implementation class DetalheResultadoServlet
 */
public class DetalheResultadoServlet extends ServletBase {
       
	private static final long serialVersionUID = 830313066445180103L;

	public DetalheResultadoServlet() {
        super();
    }

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if (user.getTipo() == Usuario.Tipo.ELEITOR) {
			throw new AreaRestritaException("Area restrita aos administradores");
		}
		
		try {
			int idVotacao = Integer.parseInt(request.getParameter("idVotacao"));

			VotacaoDao votacaoDao = DaoFactory.getInstance().getVotacaoDao();

			Votacao votacao = votacaoDao.buscarPorId(idVotacao);

			String nextJSP = "/restrito/admin/detalheResultado.jsp";
			Collections.sort(votacao.getCandidatos(),  new Comparator<Candidato>() {
				@Override
				public int compare(Candidato c1, Candidato c2) {
					return c2.getNumeroVotos() - c1.getNumeroVotos();
				}
			});
			request.setAttribute("votacao", votacao);
			int random = (int)(Math.random()*999999999);
			request.setAttribute("random", String.format("%09d", random) );
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}

}
