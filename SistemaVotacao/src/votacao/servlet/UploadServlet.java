package votacao.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.exception.BaseException;


/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			execute(request, response);
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
	}

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		try {
//			String uploadFile = request.getParameter("uploadFile");
//			if (uploadFile != null) {
				saveFile(request);
//			} else {
//
//				String nextJSP = "/upload.jsp";
//				request.getRequestDispatcher(nextJSP).forward(request, response);
//			}
		} catch (IOException e) {
			throw new BaseException(e);
		}
//		} catch (ServletException e) {
//			throw new BaseException(e);
//		}
	}

	/**
	 * @param request
	 * @throws IOException
	 */
	private void saveFile(HttpServletRequest request) throws IOException {
		File file = new File("teste2.out");
		file.createNewFile();
		System.out.println(file.getAbsoluteFile());
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		ServletInputStream in = request.getInputStream();
		
		int i = in.read();
		
		while (i != -1) {
			pw.print((char)i);
			i = in.read();
		}
		
		pw.close();
	}

}
