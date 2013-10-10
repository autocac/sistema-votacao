package votacao.dao.sqlserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Usuario.Tipo;
import votacao.bean.Votacao;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.dao.VotacaoDao;
import votacao.exception.DaoException;
import votacao.util.db.DbUtil;

public class UsuarioDaoSqlServer implements UsuarioDao {

	private static final String CAMPOS = "" +
			"LOGIN , " + 
			"SENHA , " + 
			"TIPO , " + 
			"NOME , " +
			"FL_TROCAR_SENHA ";
	
	private static final String APAGAR = "" +
			" delete from TB_USUARIO where LOGIN = ? ";
	
	private static final String ATUALIZAR = "" +
			" update TB_USUARIO " +
			" set " +
			" 	SENHA = ?, " + 
			" 	TIPO = ?, " + 
			" 	NOME = ?, " +
			" 	FL_TROCAR_SENHA = ? " +
			" where LOGIN = ? ";
	
	private static final String CRIAR = "" +
			" insert into TB_USUARIO (" +
				CAMPOS + 
			") values ( ?, ?, ?, ?, ?) ";
	
	private static final String SELECT = "" +
			" select " +
				CAMPOS +
			" from TB_USUARIO ";
	
	private static final String POR_LOGIN = "" +
		SELECT +
		" where LOGIN = ?";
	
	private static final String POR_TIPO = "" +
		SELECT +
		" where TIPO = ?";

	private static final String ELEITORADO = "" +
			" select " +
			"	U.LOGIN , " + 
			"	U.SENHA , " + 
			"	U.TIPO , " + 
			"	U.NOME , " +
			"	U.FL_TROCAR_SENHA " +
			" from TB_USUARIO U " +
			"	inner join TB_ELEITORADO E " +
			"		on U.LOGIN = E.LOGIN " +
			" where E.ID_VOTACAO = ? ";
	
	private static final String CRIAR_ELEIORADO =  "" +
	" insert into TB_ELEITORADO (LOGIN, ID_VOTACAO, FL_COMPARECEU)" +
	" values ( ?, ?, 'N') ";
	
	private static final String REMOVER_ELEIORADO =  "" +
	" delete from TB_ELEITORADO where ID_VOTACAO = ?";

	
	@Override
	public List<Usuario> buscarTodosVotantes() throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			statement = conn.prepareStatement(SELECT);
			result = statement.executeQuery();
			while (result.next()) {
				Usuario usuario = getBean(result);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return usuarios;
	}

	@Override
	public List<Usuario> buscarTodosAdministradores() throws DaoException {
		return buscarPorTipo(Tipo.ADMINISTRADOR);
	}

	@Override
	public Usuario buscarPorLogin(String login) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Usuario usuario = null;
		try {
			statement = conn.prepareStatement(POR_LOGIN);
			statement.setString(1, login);
			result = statement.executeQuery();
			if (result.next()) {
				usuario = getBean(result);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return usuario;
	}

	private Usuario getBean(ResultSet result) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setLogin(result.getString("LOGIN"));
		usuario.setNome(result.getString("NOME"));
		usuario.setSenha(result.getString("SENHA"));
		usuario.setTipo(result.getString("TIPO").charAt(0));
		usuario.setTrocarSenha("S".equalsIgnoreCase(result.getString("FL_TROCAR_SENHA")));
		return usuario;
	}

	void removerEleitores(Connection conn, int idVotacao) throws DaoException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(REMOVER_ELEIORADO);
			statement.setInt(1, idVotacao);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(null, statement, result);
		}
	}
	
	void criarEleitor(Connection conn, Usuario usuario, int idVotacao) throws DaoException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(CRIAR_ELEIORADO);
			statement.setString(1, usuario.getLogin());
			statement.setInt(2, idVotacao);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(null, statement, result);
		}
	}
	
	@Override
	public void criar(Usuario usuario) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(CRIAR);

			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getTipo().getCodigo() + "");
			statement.setString(4, usuario.getNome());
			statement.setString(5, usuario.isTrocarSenha()?"S":"N");
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
	}

	@Override
	public void salvar(Usuario usuario) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(ATUALIZAR);

			
			statement.setString(1, usuario.getSenha());
			statement.setString(2, usuario.getTipo().getCodigo() + "");
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.isTrocarSenha()?"S":"N");
			statement.setString(5, usuario.getLogin());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
	}

	@Override
	public void apagar(Usuario usuario) throws DaoException {
		apagar(usuario.getLogin());
	}

	@Override
	public void apagar(String login) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = conn.prepareStatement(APAGAR);

			statement.setString(1, login);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
	}

	@Override
	public List<Usuario> buscarPorTipo(Tipo eleitor) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			statement = conn.prepareStatement(POR_TIPO);
			statement.setString(1, eleitor.getCodigo() + "");
			result = statement.executeQuery();
			while (result.next()) {
				Usuario usuario = getBean(result);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return usuarios;
	}

	@Override
	public List<Usuario> buscarPorVotacao(int idVotacao) throws DaoException {
		Connection conn = DbUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			statement = conn.prepareStatement(ELEITORADO);
			statement.setInt(1, idVotacao);
			result = statement.executeQuery();
			while (result.next()) {
				Usuario usuario = getBean(result);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(conn, statement, result);
		}
		return usuarios;
	}

}
