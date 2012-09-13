package votacao.dao;

import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Votacao;

public class CandidatoDao {

	public List<Candidato> buscarCandidatosSemana() {
		return null;
	}

	public Candidato buscarPorId(int idVotacao, int idCandidato) {
		for (Votacao v : VotacaoDao.listaVotacoes) {
			for (Candidato c : v.getCandidatos()) {
				if (v.getId() == idVotacao && c.getId() == idCandidato) {
					return c;
				}
			}
		}
		return null;
	}
	
	
}
