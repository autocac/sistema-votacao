package votacao.dao.memory;

import java.util.ArrayList;
import java.util.List;

import votacao.bean.Comparecimento;
import votacao.dao.ComparecimentoDao;
import votacao.exception.DaoException;

class ComparecimentoDaoMemory implements ComparecimentoDao {

	static final List<Comparecimento> listaComparecimento;
	
	static {
		listaComparecimento = new ArrayList<Comparecimento>();
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.ComparecimentoDao#salvar(votacao.bean.Comparecimento)
	 */
	@Override
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
