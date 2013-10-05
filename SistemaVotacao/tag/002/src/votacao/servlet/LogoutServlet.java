package votacao.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.exception.BaseException;


/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends ServletBase {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {

		try {
			request.getSession().removeAttribute("user");
			//chutando usuario após votar
			request.getSession().invalidate();
			
			String nextJSP = "/login.jsp";
			request.getRequestDispatcher(nextJSP).forward(request, response);
		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

}
