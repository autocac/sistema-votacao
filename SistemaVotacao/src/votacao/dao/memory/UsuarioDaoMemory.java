package votacao.dao.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Usuario.Tipo;
import votacao.dao.UsuarioDao;

class UsuarioDaoMemory implements UsuarioDao {

	public static final List<Usuario> listaUsuarios;
	
	static {
		listaUsuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		usuario.setLogin("admin");
		usuario.setSenha("admin");
		usuario.setTipo('A');
		listaUsuarios.add(usuario);
		
		usuario = new Usuario();
		usuario.setLogin("ze");
		usuario.setSenha("ze");
		usuario.setTipo('E');
		listaUsuarios.add(usuario);
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#buscarTodosVotantes()
	 */
	@Override
	public List<Usuario> buscarTodosVotantes() {
		return new ArrayList<Usuario>(listaUsuarios);
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#buscarTodosAdministradores()
	 */
	@Override
	public List<Usuario> buscarTodosAdministradores() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#buscarPorLogin(java.lang.String)
	 */
	@Override
	public Usuario buscarPorLogin(String login) {
		for (Usuario u : listaUsuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#criar(votacao.bean.Usuario)
	 */
	@Override
	public void criar(Usuario usuario) {
		listaUsuarios.add(usuario);
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#salvar(votacao.bean.Usuario)
	 */
	@Override
	public void salvar(Usuario usuario) {
		Iterator<Usuario> it = listaUsuarios.iterator();
		while (it.hasNext()) {
			Usuario u = it.next();
			if (u.getLogin().equals(usuario.getLogin())) {
				it.remove();
			}
		}
		listaUsuarios.add(usuario);
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#apagar(votacao.bean.Usuario)
	 */
	@Override
	public void apagar(Usuario usuario) {
		
	}

	/* (non-Javadoc)
	 * @see votacao.dao.UsuarioDao#apagar(java.lang.String)
	 */
	@Override
	public void apagar(String login) {
		Iterator<Usuario> it = listaUsuarios.iterator();
		while (it.hasNext()) {
			Usuario u = it.next();
			if (u.getLogin().equals(login)) {
				it.remove();
			}
		}
	}

	@Override
	public List<Usuario> buscarPorTipo(Tipo eleitor) {
		
		ArrayList<Usuario> listaUsuarioTipo = new ArrayList<Usuario>(listaUsuarios);
		Iterator<Usuario> it = listaUsuarioTipo.iterator();
		while (it.hasNext()) {
			Usuario u = it.next();
			if (u.getTipo() != eleitor) {
				it.remove();
			}
		}
		return listaUsuarioTipo;
	}
}
