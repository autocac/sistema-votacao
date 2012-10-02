package votacao.dao.sqlserver;

import votacao.dao.CandidatoDao;
import votacao.dao.ComparecimentoDao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;

public class SqlServerDaoFactory extends DaoFactory {

	public SqlServerDaoFactory() {
	}

	@Override
	public UsuarioDao getUsuarioDao() {
		return null;
	}

	@Override
	public CandidatoDao getCandidatoDao() {
		return null;
	}

	@Override
	public ComparecimentoDao getComparecimentoDao() {
		return null;
	}

	@Override
	public VotacaoDao getVotacaoDao() {
		return null;
	}

}
