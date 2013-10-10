package votacao.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.DaoException;
import votacao.util.db.DbUtil;

public class VotacaoDaoSqlServer implements VotacaoDao {

	private static final String CAMPOS = "" +
		"DESCRICAO , " + 
		"DT_INI , " + 
		"DT_FIM , " +
		"LOGIN_ADMIN, " +
		"FL_SECRETA ," +
		"ID_VOTACAO ";
	
	private static final String APAGAR = "" +
		" delete from TB_VOTACAO where ID_VOTACAO = ? ";
	
	private static final String ATUALIZAR = "" +
		" update TB_VOTACAO " +
		" set " +
		"	DESCRICAO = ?, " + 
		"	DT_INI = ?, " + 
		"	DT_FIM = ?, " +
		"	LOGIN_ADMIN = ?, " +
		"	FL_SECRETA = ? " +
		" where ID_VOTACAO = ? ";
	
	private static final String CRIAR = "" +
		" insert into TB_VOTACAO (" +
			CAMPOS + 
		") values ( ?, ?, ?, ?, ?, ?) ";
	
	private static final String SELECT = "" +
		" select " +
			CAMPOS +
		" from TB_VOTACAO ";
	
	private static final String POR_ID = "" +
		SELECT +
		" where ID_VOTACAO = ?";
	
	private static final String POR_TIPO = "" +
		SELECT +
		" where TIPO = ?";
	
	private static final String POR_ELEITOR = "" +
		" select " +
		"	V.ID_VOTACAO , " + 
		"	V.DESCRICAO , " + 
		"	V.DT_INI , " + 
		"	V.DT_FIM , " +
		"	V.LOGIN_ADMIN, " +
		"	V.FL_SECRETA " +
		" from TB_VOTACAO V " +
		"	inner join TB_ELEITORADO E " +
		"		on V.ID_VOTACAO = E.ID_VOTACAO " +
		" where E.LOGIN = ? ";
	
	@Override
	public synchronized void criar(Votacao votacao) throws DaoException {
		int novoId = DbUtil.getProximoValorSequence("TB_VOTACAO", "ID_VOTACAO");
		Connection conn = DbUtil.getConnection(false);
		
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(CRIAR);

			
			setStatement(votacao, novoId, statement);

			statement.executeUpdate();
			
			votacao.setId(novoId);
			
			UsuarioDaoSqlServer daoUsuario = (UsuarioDaoSqlServer)DaoFactory.getInstance().getUsuarioDao();
			for (Usuario eleitor : votacao.getEleitorado()) {
				daoUsuario.criarEleitor(conn, eleitor, novoId);
			}
			
			CandidatoDaoSqlServer daoCandidato = (CandidatoDaoSqlServer)DaoFactory.getInstance().getCandidatoDao();
			for (Candidato candidato : votacao.getCandidatos()) {
				candidato.setIdVotacao(votacao.getId());
				daoCandidato.criar(conn, candidato);
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DaoException(e1);
			}
			throw new DaoException(e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DaoException(e);
			}
			DbUtil.close(conn, statement, result);
		}
	}
	
	@Override
	public synchronized void salvar(Votacao votacao) throws DaoException {
		Connection conn = DbUtil.getConnection(false);
		
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(ATUALIZAR);

			setStatement(votacao, statement);

			statement.executeUpdate();
			
			UsuarioDaoSqlServer daoUsuario = (UsuarioDaoSqlServer)DaoFactory.getInstance().getUsuarioDao();
			daoUsuario.removerEleitores(conn, votacao.getId());
			for (Usuario eleitor : votacao.getEleitorado()) {
				daoUsuario.criarEleitor(conn, eleitor, votacao.getId());
			}
			
			CandidatoDaoSqlServer daoCandidato = (CandidatoDaoSqlServer)DaoFactory.getInstance().getCandidatoDao();
			daoCandidato.apagarPorVotacao(conn, votacao.getId());
			for (Candidato candidato : votacao.getCandidatos()) {
				candidato.setIdVotacao(votacao.getId());
				daoCandidato.criar(conn, candidato);
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new DaoException(e1);
			}
			throw new DaoException(e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DaoException(e);
			}
			DbUtil.close(conn, statement, result);
		}
	}

	private void setStatement(
			Votacao votacao, 
			PreparedStatement statement) throws SQLException {
		setStatement(votacao, votacao.getId(), statement);
	}
	
	private void setStatement(
			Votacao votacao, 
			int novoId,
			PreparedStatement statement) throws SQLException {
		statement.setString(1, votacao.getDescricao());
		statement.setTimestamp(2, DbUtil.getTimestamp(votacao.getPeriodo().getDataInicio()));
		statement.setTimestamp(3, DbUtil.getTimestamp(votacao.getPeriodo().getDataFim()));
		statement.setString(4, votacao.getAdministrador().getLogin());
		statement.setString(5, votacao.isSecreta()?"S":"N");
		statement.setInt(6, novoId);
	}

	@Override
	public Votacao buscarPorId(int idVotacao) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Votacao votacao = null;
		try {
			statement = conn.prepareStatement(POR_ID);
			statement.setInt(1, idVotacao);
			result = statement.executeQuery();
			if (result.next()) {
				votacao = getBean(result);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return votacao;
	}

	private Votacao getBean(ResultSet result) throws SQLException, DaoException {
		Votacao votacao = new Votacao();
		votacao.setId(result.getInt("ID_VOTACAO"));
		votacao.setDescricao(result.getString("DESCRICAO"));
		
		Date dtIni = DbUtil.getJavaDate(result, "DT_INI", java.sql.Timestamp.class);
		Date dtFim = DbUtil.getJavaDate(result, "DT_FIM", java.sql.Timestamp.class);
		Periodo p = new Periodo(dtIni, dtFim);
		votacao.setPeriodo(p);
		
		UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
		Usuario admin = usuarioDao.buscarPorLogin(result.getString("LOGIN_ADMIN"));
		votacao.setAdministrador(admin);
		
		votacao.setSecreta("S".equalsIgnoreCase(result.getString("FL_SECRETA")));
		
		CandidatoDao candidatoDao = DaoFactory.getInstance().getCandidatoDao();
		List<Candidato> candidatos = candidatoDao.buscarPorVotacao(votacao.getId());
		votacao.setCandidatos(candidatos);
		
		List<Usuario> eleitorado = usuarioDao.buscarPorVotacao(votacao.getId());
		votacao.setEleitorado(eleitorado);
		return votacao;
	}

	@Override
	public Votacao buscarVotacaoAberta() {
		return null;
	}

	@Override
	public void terminarVotacao(Votacao votacao) {

	}

	@Override
	public List<Votacao> buscarPorUsuario(Usuario user) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Votacao> votacoes = new ArrayList<Votacao>();
		try {
			statement = conn.prepareStatement(POR_ELEITOR);
			statement.setString(1, user.getLogin());
			result = statement.executeQuery();
			while (result.next()) {
				Votacao votacao = getBean(result);
				votacoes.add(votacao);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return votacoes;
	}
	
	@Override
	public List<Votacao> buscarTodas() throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Votacao> votacoes = new ArrayList<Votacao>();
		try {
			statement = conn.prepareStatement(SELECT);
			result = statement.executeQuery();
			while (result.next()) {
				Votacao votacao = getBean(result);
				votacoes.add(votacao);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return votacoes;
	}

}
