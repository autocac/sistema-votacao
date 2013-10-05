package votacao.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Candidato;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;
import votacao.exception.UploadException;
import votacao.util.UploadManager;


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
		UploadManager manager = new UploadManager(request);
		try {
			manager.uploadFormToMemory();
		} catch (UploadException e) {
			throw new BaseException(e);
		}
		
		Map<String, Object> param = manager.getParametros();
		String descricao = manager.getString("txtDescricao");
		int numDiasEncerramento = manager.getInt("numDiasEncerramento");
		String[] participantes = manager.getString("participantes").split(";");
		List titulosList = manager.getList("txtTitulo");
		List descCandidatoList = manager.getList("txtDescricaoCandidato");
		List btnUploadFile = manager.getList("btnUpload_file");
		List btnUpload = manager.getList("btnUpload");
		
		
		List<Candidato> candidatos = new ArrayList<Candidato>();
		for (int i = 0;i < titulosList.size() ;i++) {
			String titulo = (String)titulosList.get(i);
			String descCandidato = (String)descCandidatoList.get(i);
			String fileName = (String)btnUploadFile.get(i);
			System.out.println(fileName);
			byte[] arrayImagem = (byte[])btnUpload.get(i);
			
			Candidato candidato = new Candidato();
			candidato.setNome(titulo);
			candidato.setDescricao(descCandidato);
			candidato.setImagem(arrayImagem);
			
			candidatos.add(candidato);
		}
		
		List<Usuario> eleitorado = new ArrayList<Usuario>();
		UsuarioDao dao = DaoFactory.getInstance().getUsuarioDao();
		for (String login : participantes) {
			Usuario user = dao.buscarPorLogin(login);
			eleitorado.add(user);
		}
		
		Votacao votacao = new Votacao();
	}
	
	public void execute2(HttpServletRequest request, HttpServletResponse response)
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
