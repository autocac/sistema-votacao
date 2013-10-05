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
	
	public Comparecimento buscarPorId(int idVotacao, String login) {
		Comparecimento presenca = null;
		for (Comparecimento c : listaComparecimento) {
			if (c.getIdVotacao() == idVotacao 
					&& c.getLoginUsuario().equals(login)) {
				 presenca = c;
			}
		}
		return presenca;
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.ComparecimentoDao#salvar(votacao.bean.Comparecimento)
	 */
	@Override
	public void salvar(Comparecimento comparecimento) throws DaoException{
		if (null != buscarPorId(comparecimento.getIdVotacao(), comparecimento.getLoginUsuario())) {
			throw new DaoException("Este usuario ja compareceu e votou nesta votação");
		}
		listaComparecimento.add(comparecimento);
	}

}
