package votacao.dao;

import votacao.dao.sqlserver.SqlServerDaoFactory;

public abstract class DaoFactory {
	
	private static DaoFactory instance = new SqlServerDaoFactory();
	
	public static DaoFactory getInstance() {
		return DaoFactory.instance;
	}
	
	public abstract UsuarioDao getUsuarioDao();
	
	public abstract CandidatoDao getCandidatoDao();
	
	public abstract ComparecimentoDao getComparecimentoDao();
	
	public abstract VotacaoDao getVotacaoDao();
	
}
