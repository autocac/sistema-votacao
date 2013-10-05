package votacao.dao.memory;

import votacao.dao.CandidatoDao;
import votacao.dao.ComparecimentoDao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;

public class MemoryDaoFactory extends DaoFactory {

	@Override
	public UsuarioDao getUsuarioDao() {
		return new UsuarioDaoMemory();
	}

	@Override
	public CandidatoDao getCandidatoDao() {
		return new CandidatoDaoMemory();
	}

	@Override
	public ComparecimentoDao getComparecimentoDao() {
		return new ComparecimentoDaoMemory();
	}

	@Override
	public VotacaoDao getVotacaoDao() {
		return new VotacaoDaoMemory();
	}

}
