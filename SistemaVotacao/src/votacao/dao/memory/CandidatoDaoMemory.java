package votacao.dao.memory;

import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;

class CandidatoDaoMemory implements CandidatoDao {

	/* (non-Javadoc)
	 * @see votacao.dao.CandidatoDao#buscarCandidatosSemana()
	 */
	@Override
	public List<Candidato> buscarCandidatosSemana() {
		return null;
	}

	/* (non-Javadoc)
	 * @see votacao.dao.CandidatoDao#buscarPorId(int, int)
	 */
	@Override
	public Candidato buscarPorId(int idVotacao, int idCandidato) {
		for (Votacao v : VotacaoDaoMemory.listaVotacoes) {
			for (Candidato c : v.getCandidatos()) {
				if (v.getId() == idVotacao && c.getId() == idCandidato) {
					return c;
				}
			}
		}
		return null;
	}
	
	
}
