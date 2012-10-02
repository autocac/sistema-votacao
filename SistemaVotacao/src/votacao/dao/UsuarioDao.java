package votacao.dao;

import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Usuario.Tipo;
import votacao.exception.DaoException;

public interface UsuarioDao {

	public abstract List<Usuario> buscarTodosVotantes() throws DaoException;

	public abstract List<Usuario> buscarTodosAdministradores() throws DaoException;

	public abstract Usuario buscarPorLogin(String login) throws DaoException;

	public abstract void criar(Usuario usuario) throws DaoException;

	public abstract void salvar(Usuario usuario) throws DaoException;

	public abstract void apagar(Usuario usuario) throws DaoException;

	public abstract void apagar(String login) throws DaoException;

	public abstract List<Usuario> buscarPorTipo(Tipo eleitor) throws DaoException;
	
	public abstract List<Usuario> buscarPorVotacao(int idVotacao) throws DaoException;

}