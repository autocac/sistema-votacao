package votacao.dao;

import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Usuario.Tipo;

public interface UsuarioDao {

	public abstract List<Usuario> buscarTodosVotantes();

	public abstract List<Usuario> buscarTodosAdministradores();

	public abstract Usuario buscarPorLogin(String login);

	public abstract void criar(Usuario usuario);

	public abstract void salvar(Usuario usuario);

	public abstract void apagar(Usuario usuario);

	public abstract void apagar(String login);

	public abstract List<Usuario> buscarPorTipo(Tipo eleitor);

}