package votacao.util.db;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import votacao.exception.DaoException;


public class DbUtil {

	private static final Logger LOG = Logger.getLogger(DbUtil.class);
	
//	private static final String URL_DATABASE = "jdbc:sqlserver://localhost:52796;databaseName=SIS_VOT;";
//	private static final String DRIVER_JDBC = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	
//	private static final String USUARIO_DB = "prof";
//	private static final String SENHA_USUARIO_DB = "prof123";
	
	private static String URL_DATABASE = "";
	private static String DRIVER_JDBC = "";
	
	private static String USUARIO_DB = "";
	private static String SENHA_USUARIO_DB = "";
	
	private static Properties conf;
	
	public synchronized static void loadConf(HttpServletRequest request) {
		if (conf == null) {
			Properties properties = new Properties();
			ServletContext myContext = request.getSession().getServletContext();

			InputStream is = myContext.getResourceAsStream( "/WEB-INF/classes/conf.properties" );
		
			try { 
			    properties.load( is ); 
			    URL_DATABASE = properties.getProperty("database.url");
			    DRIVER_JDBC = properties.getProperty("database.driver");
			    USUARIO_DB = properties.getProperty("database.user");
			    SENHA_USUARIO_DB = properties.getProperty("database.pass");
			    
				LOG.info("URL DATABASE : " + URL_DATABASE);
				LOG.info("DRIVER JDBC : " + DRIVER_JDBC);
				LOG.info("Carregando driver ");
				Class.forName(DRIVER_JDBC);
				LOG.info("Driver carregado");
			} catch (Exception e) { 
			    System.out.println("--- Erro Carregando Arquivo de Configuração ---"); 
			    e.printStackTrace();
			}
		}
	}

//	static {
//		try {
//			LOG.info("URL DATABASE : " + URL_DATABASE);
//			LOG.info("DRIVER JDBC : " + DRIVER_JDBC);
//			LOG.info("Carregando driver ");
//			Class.forName(DRIVER_JDBC);
//			LOG.info("Driver carregado");
//		} catch (ClassNotFoundException e) {
//			LOG.error(e.getMessage(), e);
//			e.printStackTrace();
//		}
//	}
	
	public static Connection getConnection() throws DaoException {
		return getConnection(true);
	}
	
	public static Connection getConnection(boolean autoCommit) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection(URL_DATABASE, USUARIO_DB, SENHA_USUARIO_DB);
			connection.setAutoCommit(autoCommit);
			LOG.debug("Conexao " + connection + " obtida");
			return connection;
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	public static java.util.Date getJavaDate(ResultSet result, String nomeCampo) throws SQLException {
		Date dataFalecimento = result.getDate(nomeCampo);
		java.util.Date javaDate = null;
		if (dataFalecimento != null) {
			javaDate = new Date(dataFalecimento.getTime());
		}
		return javaDate;
	}
	
	public static byte[] getImage(ResultSet result, String nomeCampo) throws DaoException {
		InputStream is = null;
		try {

			return result.getBytes(nomeCampo);

		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new DaoException(e);
				}
			}
		}

	}
	
	public static Date getSqlDate(java.util.Date date) {
		Date sqlDate = null;
		if (date != null) {
			sqlDate = new java.sql.Date(date.getTime());
		}
		return sqlDate;
	}
	
	public static void close(Connection conn, Statement statement, ResultSet result) {
		try {
			if (conn != null) {
				LOG.debug("Fechando conexao " + conn);
				conn.close();
				LOG.debug("Conexao " + conn + " fechada");
			}
			if (statement != null) {
				LOG.debug("Fechando statement " + statement);
				statement.close();
				LOG.debug("Statement " + statement + " fechado");
			}
			if (result != null) {
				LOG.debug("Fechando ResultSet " + result);
				result.close();
				LOG.debug("ResultSet " + result + " fechado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getArrayImage(Image img) throws DaoException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream(8000);
			
			BufferedImage bufferedImage = 
				new BufferedImage(
						img.getWidth(null), 
						img.getHeight(null), 
						BufferedImage.TYPE_INT_RGB);
			
			
			ImageIO.write(bufferedImage, "jpg", baos);
			
			baos.flush();
			
			return baos.toByteArray();
			
		} catch (IOException e) {
			throw new DaoException(e);
		} finally {
			close(baos);
		}
	}

	public static void close(Closeable closeable) throws DaoException {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new DaoException(e);
			}
		}
	}
	
	public static int getProximoValorSequence(String tabela, String pk) throws DaoException {
		Connection conn = DbUtil.getConnection();
		try {
			return getProximoValorSequence(conn, tabela, pk);
		} finally {
			DbUtil.close(conn, null, null);
		}
	}
	
	public static int getProximoValorSequence(Connection conn, String tabela, String pk) throws DaoException {
		
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoId = 0;
		try {
			
			statement = conn.prepareStatement("select isnull(max(" + pk + "), 0) + 1 as NOVO_ID from " + tabela);
			result = statement.executeQuery();
			if (result.next()) {

				novoId = result.getInt("NOVO_ID");
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DbUtil.close(null, statement, result);
		}
		return novoId;
	}
	
}
