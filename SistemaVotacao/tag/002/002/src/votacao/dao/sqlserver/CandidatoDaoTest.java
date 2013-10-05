package votacao.dao.sqlserver;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import votacao.bean.Usuario;
import votacao.dao.DaoFactory;
import votacao.dao.UsuarioDao;
import votacao.util.db.DbUtil;

public class CandidatoDaoTest {

	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			System.out.println(new File(".").getAbsoluteFile());
			prop.load(new FileInputStream(new File("src/conf.properties")));
			DbUtil.loadByProperties(prop);
			UsuarioDao usuarioDao = DaoFactory.getInstance().getUsuarioDao();
			List<Usuario> adminList = usuarioDao.buscarTodosAdministradores();
			for (Usuario usuario : adminList) {
				System.out.println(usuario);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
