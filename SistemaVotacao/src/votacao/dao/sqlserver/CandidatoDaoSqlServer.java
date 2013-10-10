package votacao.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Votacao;
import votacao.dao.CandidatoDao;
import votacao.exception.DaoException;
import votacao.util.db.DbUtil;

public class CandidatoDaoSqlServer implements CandidatoDao {

	private static final String CAMPOS = "" +
		"ID_CANDIDATO , " + 
		"ID_VOTACAO , " + 
		"NOME , " + 
		"IMAGEM , " +
		"IMG_CONT_TYPE, " +
		"DESCRICAO, " +
		"NUMERO_VOTOS ";
	
	private static final String SELECT = "" +
		" select " +
			CAMPOS +
		" from TB_CANDIDATO ";

	private static final String POR_VOTACAO = "" +
		SELECT + 
		" where ID_VOTACAO = ? ";
	
	private static final String POR_VOTACAO_CANDIDATO = "" +
		SELECT + 
		" where ID_VOTACAO = ? and ID_CANDIDATO = ? ";
	
	private static final String ATUALIZAR = "" +
		" update TB_CANDIDATO " +
		" set " +
		"	NOME = ? , " + 
		"	IMAGEM = ? , " +
		"	IMG_CONT_TYPE = ? , " +
		"	DESCRICAO = ? , " +
		"	NUMERO_VOTOS = ? " +
		" where ID_CANDIDATO  = ? and ID_VOTACAO = ? ";
	
	private static final String CRIAR = "" +
	" insert into TB_CANDIDATO (" +
		CAMPOS + 
	") values ( ?, ?, ?, ?, ?, ?, ?) ";
	
	private static final String APAGAR_POR_VOTACAO = "" +
			" delete from TB_CANDIDATO where ID_VOTACAO = ? ";
			

	void apagarPorVotacao(Connection conn,  int idVotacao) throws DaoException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(APAGAR_POR_VOTACAO);

			statement.setInt(1, idVotacao);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(null, statement, result);
		}
	}
	
	void criar(Connection conn,  Candidato candidato) throws DaoException {
		int novoId = DbUtil.getProximoValorSequence(conn, "TB_CANDIDATO", "ID_CANDIDATO");
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(CRIAR);

			statement.setInt(1, novoId);
			statement.setInt(2, candidato.getIdVotacao());
			statement.setString(3, candidato.getNome());
			statement.setBytes(4, candidato.getImagem());
			statement.setString(5, candidato.getImageContentType());
			statement.setString(6, candidato.getDescricao());
			statement.setInt(7, candidato.getNumeroVotos());
			
			statement.executeUpdate();
			
			candidato.setId(novoId);
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(null, statement, result);
		}
	}
	
	@Override
	public List<Candidato> buscarCandidatosSemana() {
		return null;
	}

	@Override
	public Candidato buscarPorId(int idVotacao, int idCandidato) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Candidato votacao = null;
		try {
			statement = conn.prepareStatement(POR_VOTACAO_CANDIDATO);
			statement.setInt(1, idVotacao);
			statement.setInt(2, idCandidato);
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
	
	private Candidato getBean(ResultSet result) throws SQLException, DaoException {
		Candidato candidato = new Candidato();
		candidato.setId(result.getInt("ID_CANDIDATO"));
		candidato.setIdVotacao(result.getInt("ID_VOTACAO"));
		candidato.setNome(result.getString("NOME"));
		candidato.setImagem(DbUtil.getImage(result, "IMAGEM"));
		candidato.setImageContentType(result.getString("IMG_CONT_TYPE"));
		candidato.setDescricao(result.getString("DESCRICAO"));
		candidato.setNumeroVotos(result.getInt("NUMERO_VOTOS"));
		return candidato;
	}

	@Override
	public void salvar(Candidato candidato) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(ATUALIZAR);
			statement.setString(1, candidato.getNome());
			statement.setBytes(2, candidato.getImagem());
			statement.setString(3, candidato.getImageContentType());
			statement.setString(4, candidato.getDescricao());
			statement.setInt(5, candidato.getNumeroVotos());
			statement.setInt(6, candidato.getId());
			statement.setInt(7, candidato.getIdVotacao());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, null);
		}
	}

	@Override
	public List<Candidato> buscarPorVotacao(int idVotacao) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		try {
			statement = conn.prepareStatement(POR_VOTACAO);
			statement.setInt(1, idVotacao);
			result = statement.executeQuery();
			while (result.next()) {
				Candidato candidato = getBean(result);
				candidatos.add(candidato);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return candidatos;
	}

}
