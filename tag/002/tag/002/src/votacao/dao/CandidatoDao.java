package votacao.dao;

import java.util.List;

import votacao.bean.Candidato;
import votacao.exception.DaoException;

public interface CandidatoDao {

	public abstract List<Candidato> buscarCandidatosSemana() throws DaoException ;

	public abstract Candidato buscarPorId(int idVotacao, int idCandidato) throws DaoException ;
	
	public abstract List<Candidato> buscarPorVotacao(int idVotacao) throws DaoException ;

	public abstract void salvar(Candidato candidato) throws DaoException ;

}