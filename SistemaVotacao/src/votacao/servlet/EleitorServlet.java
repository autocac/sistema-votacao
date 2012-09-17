package votacao.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Usuario;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;


/**
 * Servlet implementation class EleitorServlet
 */
public class EleitorServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			String msg = "";
			String acao = request.getParameter("acao");
			String login = request.getParameter("login");
			Usuario user = null;
			
			if ("criar".equals(acao) || "salvar".equals(acao)) {
				if (login != null && login.length() > 0) {
					String senha = request.getParameter("senha");
					String nome = request.getParameter("nome");
					
					UsuarioDao usuarioDao = new UsuarioDao();
					user = usuarioDao.buscarPorLogin(login);
					if (user == null) {
						user = new Usuario();
						user.setLogin(login);
						user.setTipo(Usuario.Tipo.ELEITOR);
					}
					user.setNome(nome);
					if (senha != null && senha.trim().length() > 0) {
						user.setSenha(senha);
					}
					
					msg = "Usuario criado com sucesso";
					if ("criar".equals(acao)) {
						usuarioDao.criar(user);
					} else if ("salvar".equals(acao)) {
						usuarioDao.salvar(user);
						msg = "Usuario atualizado com sucesso";
					}
					request.setAttribute("msg", msg);
				}

			} else if ("apagar".equals(acao)) {
				UsuarioDao usuarioDao = new UsuarioDao();
				usuarioDao.apagar(login);
				msg = "Usuario apagado com sucesso";
				request.setAttribute("msg", msg);
			} else {
				UsuarioDao usuarioDao = new UsuarioDao();
				user = usuarioDao.buscarPorLogin(login);
			}
			
			if (user != null) {
				setUsuarioRequest(user, request);
			}
	
			String nextJSP = "/restrito/admin/eleitor.jsp";
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

	private void setUsuarioRequest(Usuario user, HttpServletRequest request) {
		request.setAttribute("login", user.getLogin());
		request.setAttribute("senha", "");
		request.setAttribute("nome", user.getNome());
	}
}
