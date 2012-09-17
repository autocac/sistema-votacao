package votacao.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import votacao.bean.Usuario;

public class UsuarioDao {

	private static final List<Usuario> listaUsuarios;
	
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
	
	public List<Usuario> buscarTodosVotantes() {
		return new ArrayList<Usuario>(listaUsuarios);
	}
	
	public List<Usuario> buscarTodosAdministradores() {
		return null;
	}
	
	public Usuario buscarPorLogin(String login) {
		for (Usuario u : listaUsuarios) {
			if (u.getLogin().equals(login)) {
				return u;
			}
		}
		return null;
	}
	
	public void criar(Usuario usuario) {
		listaUsuarios.add(usuario);
	}
	
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
	
	public void apagar(Usuario usuario) {
		
	}

	public void apagar(String login) {
		Iterator<Usuario> it = listaUsuarios.iterator();
		while (it.hasNext()) {
			Usuario u = it.next();
			if (u.getLogin().equals(login)) {
				it.remove();
			}
		}
	}
}
