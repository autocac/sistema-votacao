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
		return new UsuarioDaoSqlServer();
	}

	@Override
	public CandidatoDao getCandidatoDao() {
		return new CandidatoDaoSqlServer();
	}

	@Override
	public ComparecimentoDao getComparecimentoDao() {
		return new ComparecimentoDaoSqlServer();
	}

	@Override
	public VotacaoDao getVotacaoDao() {
		return new VotacaoDaoSqlServer();
	}

}
