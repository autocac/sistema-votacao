package votacao.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import votacao.bean.Comparecimento;
import votacao.bean.Votacao;
import votacao.dao.ComparecimentoDao;
import votacao.exception.DaoException;
import votacao.util.db.DbUtil;

public class ComparecimentoDaoSqlServer implements ComparecimentoDao {

	private static final String CAMPOS = "" +
		"LOGIN , " + 
		"ID_VOTACAO , " + 
		"ID_CANDIDATO_VOTADO ";
	
	private static final String SELECT = "" +
		" select " +
			CAMPOS +
		" from TB_ELEITORADO ";

	private static final String POR_ID = "" +
		SELECT +
		" where  LOGIN = ? and ID_VOTACAO = ? and FL_COMPARECEU = 'S'";

	
	private static final String ATUALIZAR = "" +
		" update TB_ELEITORADO " +
		" set " +
		"	FL_COMPARECEU = 'S'," +
		"	ID_CANDIDATO_VOTADO = ? " +
		" where LOGIN = ? and ID_VOTACAO = ? ";
		
	@Override
	public void salvar(Comparecimento comparecimento) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(ATUALIZAR);

			
			statement.setInt(1, comparecimento.getIdCandidato());
			statement.setString(2, comparecimento.getLoginUsuario());
			statement.setInt(3, comparecimento.getIdVotacao());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
	}

	@Override
	public Comparecimento buscarPorId(int idVotacao, String login) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Comparecimento votacao = null;
		try {
			statement = conn.prepareStatement(POR_ID);
			statement.setString(1, login);
			statement.setInt(2, idVotacao);
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
	
	private Comparecimento getBean(ResultSet result) throws SQLException, DaoException {
		Comparecimento votacao = new Comparecimento();
		votacao.setLoginUsuario(result.getString("LOGIN"));
		votacao.setIdVotacao(result.getInt("ID_VOTACAO"));
		votacao.setIdCandidato(result.getInt("ID_CANDIDATO_VOTADO"));
		
		return votacao;
	}

}
