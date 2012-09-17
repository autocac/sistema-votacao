package votacao.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import votacao.bean.Usuario;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;
import votacao.exception.UsuarioOuSenhaInvalidosException;

public abstract class ServletBase extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ServletBase() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			if (session.isNew()) {
				redirectLogin(request, response);
			} else {
				Usuario user = (Usuario)session.getAttribute("user");
			
				if (user == null) {
					String usuario = request.getParameter("user");
					String senha = request.getParameter("pass");

					if (usuario != null) {
						UsuarioDao usuarioDao = new UsuarioDao();
						user = usuarioDao.buscarPorLogin(usuario);
						if (user == null || !user.isSenhaOk(senha)) {
							throw new UsuarioOuSenhaInvalidosException("Usuário ou senha inválidos");
						}
						request.setAttribute("user", user);
						session.setAttribute("user", user);
					}
				}
				execute(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			try {
				redirectLogin(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void redirectLogin(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextJSP = "/login.jsp";
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextJSP);
//		String servletPath = request.getServletPath();
//		int ini = servletPath.lastIndexOf("/") + 1;
//		request.setAttribute("action", servletPath.substring(ini));
		dispatcher.forward(request,response);
	}
	
	public abstract void execute(
			HttpServletRequest request, 
			HttpServletResponse response)  throws BaseException;
}
