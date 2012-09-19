package votacao.dao;

import votacao.bean.Comparecimento;
import votacao.exception.DaoException;

public interface ComparecimentoDao {

	public abstract void salvar(Comparecimento comparecimento)
			throws DaoException;

}