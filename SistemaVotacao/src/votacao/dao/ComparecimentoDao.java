package votacao.dao;

import java.util.ArrayList;
import java.util.List;

import votacao.bean.Comparecimento;
import votacao.exception.DaoException;

public class ComparecimentoDao {

	static final List<Comparecimento> listaComparecimento;
	
	static {
		listaComparecimento = new ArrayList<Comparecimento>();
	}
	
	public void salvar(Comparecimento comparecimento) throws DaoException{
		for (Comparecimento c : listaComparecimento) {
			if (c.getIdVotacao() == comparecimento.getIdVotacao() 
					&& c.getLoginUsuario().equals(comparecimento.getLoginUsuario())) {
				throw new DaoException("Este usuario ja compareceu e votou nesta vota��o");
			}
		}
		listaComparecimento.add(comparecimento);
	}

}
