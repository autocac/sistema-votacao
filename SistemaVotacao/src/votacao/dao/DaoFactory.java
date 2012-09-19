package votacao.dao;

import votacao.dao.memory.MemoryDaoFactory;

public abstract class DaoFactory {
	
	private static DaoFactory instance = new MemoryDaoFactory();
	
	public static DaoFactory getInstance() {
		return DaoFactory.instance;
	}
	
	public abstract UsuarioDao getUsuarioDao();
	
	public abstract CandidatoDao getCandidatoDao();
	
	public abstract ComparecimentoDao getComparecimentoDao();
	
	public abstract VotacaoDao getVotacaoDao();
	
}
