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
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;
import votacao.exception.SessaoInvalidaException;
import votacao.exception.UsuarioOuSenhaInvalidosException;
import votacao.util.db.DbUtil;

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
			DbUtil.loadConf(request);
			HttpSession session = request.getSession();
			if (session.isNew()) {
				redirectLogin(request, response);
			} else {
				UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
				
				Usuario user = (Usuario)session.getAttribute("user");
			
				if (user == null) {
					String usuario = request.getParameter("user");
					String senha = request.getParameter("pass");

					if (usuario == null) {
						throw new SessaoInvalidaException("Sessão inv�lida");
					}
						
					user = usuarioDao.buscarPorLogin(usuario);
					if (user == null || !user.isSenhaOk(senha)) {
						throw new UsuarioOuSenhaInvalidosException("Usuário ou senha inv�lidos");
					}
					request.setAttribute("user", user);
					session.setAttribute("user", user);
					
				} else {
					String senha1 = request.getParameter("newPass1");
					String senha2 = request.getParameter("newPass2");
					if (senha1 != null && senha2 != null) {

						if (senha1.equals(senha2)) {
							user.setSenha(senha1);
							user.setTrocarSenha(false);
							usuarioDao.salvar(user);
							request.setAttribute("msg", "Senha alterada com sucesso");
						} else {
							request.setAttribute("msg", "Confirmação de senha inválida");
						}
					}
				}
				if (user != null && user.isTrocarSenha()) {
					redirectTrocarSenha(request, response);
				} else {
					execute(request, response);
				}
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

	private void redirectTrocarSenha(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextJSP = "/restrito/eleitor/trocarSenha.jsp";
		redirect(request, response, nextJSP);		
	}

	private void redirectLogin(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nextJSP = "/login.jsp";
		redirect(request, response, nextJSP);
	}
	
	protected void redirect(
			HttpServletRequest request,
			HttpServletResponse response,
			String pagina) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
		dispatcher.forward(request,response);
	}
	
	public abstract void execute(
			HttpServletRequest request, 
			HttpServletResponse response)  throws BaseException;
}
