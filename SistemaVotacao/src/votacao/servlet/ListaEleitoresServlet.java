package votacao.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Usuario;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;


/**
 * Servlet implementation class ListaEleitoresServlet
 */
public class ListaEleitoresServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			
			String nome = request.getParameter("nome");
			String login = request.getParameter("login");

			UsuarioDao usuarioDao = new UsuarioDao();
			
			List<Usuario> lista = usuarioDao.buscarTodosVotantes();

			request.setAttribute("eleitores", lista);
			String nextJSP = "/restrito/admin/listaEleitores.jsp";
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

}
