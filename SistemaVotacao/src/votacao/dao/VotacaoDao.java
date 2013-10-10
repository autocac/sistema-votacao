package votacao.dao;

import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.exception.DaoException;

public interface VotacaoDao {

	public abstract void criar(Votacao votacao) throws DaoException ;

	public abstract Votacao buscarPorId(int idVotacao) throws DaoException ;

	public abstract Votacao buscarVotacaoAberta() throws DaoException ;

	public abstract void terminarVotacao(Votacao votacao) throws DaoException ;

	public abstract List<Votacao> buscarPorUsuario(Usuario user) throws DaoException ;

	public List<Votacao> buscarTodas() throws DaoException;

	void salvar(Votacao votacao) throws DaoException;
}