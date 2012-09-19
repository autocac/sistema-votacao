package votacao.dao;

import java.util.List;

import votacao.bean.Usuario;

public interface UsuarioDao {

	public abstract List<Usuario> buscarTodosVotantes();

	public abstract List<Usuario> buscarTodosAdministradores();

	public abstract Usuario buscarPorLogin(String login);

	public abstract void criar(Usuario usuario);

	public abstract void salvar(Usuario usuario);

	public abstract void apagar(Usuario usuario);

	public abstract void apagar(String login);

}