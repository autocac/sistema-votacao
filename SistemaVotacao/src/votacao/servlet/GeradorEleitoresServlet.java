package votacao.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import votacao.bean.Turma;
import votacao.bean.Usuario;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.exception.BaseException;
import votacao.exception.DaoException;
import votacao.util.GeradorTurma;


/**
 * Servlet implementation class GeradorEleitoresServlet
 */
public class GeradorEleitoresServlet extends ServletBase {
	private static final long serialVersionUID = 1L;
	private static final int TAMANHO_SENHA = 4;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws BaseException {
		try {
			String acao = request.getParameter("acao");
			
			if ("criar".equals(acao)) {
				String listaTurmas = request.getParameter("turmas");
				String alteracaoObrigatoria = request.getParameter("chkAlteracaoObrigatoria");
				boolean trocarSenha = "on".equalsIgnoreCase(alteracaoObrigatoria);
				System.out.println("trocarSenha = " + trocarSenha);
				
				List<Turma> turmas = new ArrayList<Turma>();
				
				GeradorTurma geradorTurma = new GeradorTurma(TAMANHO_SENHA, trocarSenha);
				
				String[] arrayTurmas = listaTurmas.split(";");
				for (String turma : arrayTurmas) {
					String[] arrayTurma = turma.split("-");
					String id = arrayTurma[0].trim();
					int numAlunos = Integer.parseInt(arrayTurma[1].trim());
					turmas.add(geradorTurma.getTurma(id, numAlunos));
				}
				salvarTurmas(turmas);
				
				List<Usuario> lista = getListaUsuarios(turmas);
				
				redirectToListaUsuarios(request, response, lista);
			} else if ("listar".equals(acao)) {
				List<Usuario> lista = getListaUsuarios();
				
				redirectToListaUsuarios(request, response, lista);
			} else {
				String nextJSP = "/restrito/admin/geradorEleitores.jsp";
				redirect(request, response, nextJSP);
			}

		} catch (Exception e) {
			throw new BaseException(e); 
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param lista
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectToListaUsuarios(HttpServletRequest request,
			HttpServletResponse response, List<Usuario> lista)
			throws ServletException, IOException {
		request.setAttribute("eleitores", lista);
		String nextJSP = "/restrito/admin/listaEleitoresGerados.jsp";
		redirect(request, response, nextJSP);
	}

	private List<Usuario> getListaUsuarios() throws DaoException {
		UsuarioDao dao = DaoFactory.getInstance().getUsuarioDao();
		List<Usuario> lista = dao.buscarPorTipo(Usuario.Tipo.ELEITOR);
		Iterator<Usuario> it = lista.iterator();
		while (it.hasNext()) {
			Usuario usuario = it.next();
			if (!usuario.isTrocarSenha()) {
				it.remove();
			}
		}
		return lista;
	}
	
	/**
	 * @param turmas
	 * @return
	 */
	private List<Usuario> getListaUsuarios(List<Turma> turmas) {
		List<Usuario> lista = new ArrayList<Usuario>();
		for (Turma turma : turmas) {
			List<Usuario> eleitores = turma.getEleitores();
			for (Usuario usuario : eleitores) {
				lista.add(usuario);
			}
		}
		return lista;
	}

	
	private void salvarTurmas(List<Turma> turmas) throws DaoException {
		UsuarioDao dao = DaoFactory.getInstance().getUsuarioDao();
		for (Turma turma : turmas) {
			List<Usuario> eleitores = turma.getEleitores();
			for (Usuario usuario : eleitores) {
				Usuario userEncontrado = dao.buscarPorLogin(usuario.getLogin());
				if ( userEncontrado == null) {
					dao.criar(usuario);
				} else {
					if (userEncontrado.getTipo() == Usuario.Tipo.ELEITOR) {
						dao.salvar(usuario);
					}
				}
			}
		}
	}


	public static void main(String[] args) {
		String s = "a - 10;";
		String[] arrayTurmas = s.split(";");
		for (String turma : arrayTurmas) {
			String[] arrayTurma = turma.split("-");
			String id = arrayTurma[0].trim();
			int numAlunos = Integer.parseInt(arrayTurma[1].trim());
			
			System.out.println(id + " >> " + numAlunos);
		}
	}
}
