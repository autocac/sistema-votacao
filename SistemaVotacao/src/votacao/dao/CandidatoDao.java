package votacao.dao;

import java.util.List;

import votacao.bean.Candidato;

public interface CandidatoDao {

	public abstract List<Candidato> buscarCandidatosSemana();

	public abstract Candidato buscarPorId(int idVotacao, int idCandidato);

}