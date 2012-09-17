package votacao.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.VotacaoDao;
import votacao.exception.BaseException;

@SuppressWarnings("serial")
public class ListaVotacoesServlet extends ServletBase {

    /**
     * @see ServletBase#ServletBase()
     */
    public ListaVotacoesServlet() {
        super();
    }

	@Override
	public void execute(
			HttpServletRequest request, 
			HttpServletResponse response) throws BaseException {
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
		
		VotacaoDao votacaoDao = new VotacaoDao();
		List<Votacao> votacoes = votacaoDao.buscarPorUsuario(user);
		try {
			String nextJSP = "/restrito/eleitor/votacao.jsp";
			request.setAttribute("votacoes", votacoes);
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}
}
