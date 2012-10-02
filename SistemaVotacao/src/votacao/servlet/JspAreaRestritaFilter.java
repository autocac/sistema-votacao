package votacao.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import votacao.bean.Usuario;

/**
 * Servlet implementation class AreaRestritaServlet
 */
public class JspAreaRestritaFilter implements Filter {
	private static final long serialVersionUID = 1L;
	
	private String areaRestritaAdminPath;
	private String areaRestritaEleitorPath;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.areaRestritaAdminPath = 
			config.getInitParameter("AREA_RESTRITA_ADMIN");
		
		this.areaRestritaEleitorPath = 
			config.getInitParameter("AREA_RESTRITA_ELEITOR");
		
	}
	private boolean isValidaPath(Usuario user , String path) {
		if (user.getTipo() == Usuario.Tipo.ADMINISTRADOR) {
			return this.areaRestritaAdminPath.equals(path) || this.areaRestritaEleitorPath.equals(path);
		} else if (user.getTipo() == Usuario.Tipo.ELEITOR) {
			return this.areaRestritaEleitorPath.equals(path);
		} else {
			return false;
		}
	}
	
	private String getPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		int ini = servletPath.lastIndexOf("/");
		return servletPath.substring(0, ini);
	}
	
	private void redirectLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			String nextJSP) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextJSP);
//		String servletPath = request.getServletPath();
//		int ini = servletPath.lastIndexOf("/") + 1;
//		request.setAttribute("action", servletPath.substring(ini));
		dispatcher.forward(request,response);
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpSession session = ((HttpServletRequest)request).getSession();
			if (session.isNew()) {
				redirectLogin((HttpServletRequest)request, (HttpServletResponse)response, "/login.jsp");
			} else {
				Usuario user = (Usuario)session.getAttribute("user");
			
				if (user == null) {
					request.setAttribute("msg", "Sessão inválida");
					redirectLogin((HttpServletRequest)request, (HttpServletResponse)response, "/login.jsp");
				} else {
					//String path = ((HttpServletRequest)request).getServletPath();
					String path = getPath((HttpServletRequest)request);
					if (isValidaPath(user, path)) {
						chain.doFilter(request, response);
					} else {
						request.setAttribute("msg", "Página não encontrada");
						redirectLogin((HttpServletRequest)request, (HttpServletResponse)response, "/login.jsp");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			try {
				redirectLogin((HttpServletRequest)request, (HttpServletResponse)response, "/login.jsp");
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


	@Override
	public void destroy() {
		
	}
}
