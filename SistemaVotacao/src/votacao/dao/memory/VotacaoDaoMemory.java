package votacao.dao.memory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Periodo;
import votacao.bean.Usuario;
import votacao.bean.Votacao;
import votacao.dao.VotacaoDao;
import votacao.exception.DaoException;


class VotacaoDaoMemory implements VotacaoDao {

	static List<Votacao> listaVotacoes;
	
	static {
		Date dataCorrente = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.HOUR_OF_DAY, 24);
		Date proxDate = c.getTime();
		
		listaVotacoes = new ArrayList<Votacao>();
		Votacao v = new Votacao();
		v.setId(1);
		List<Candidato> listaCandidatos = new ArrayList<Candidato>();
		Candidato cand = new Candidato();
		cand.setId(1);
		cand.setIdVotacao(1);
		cand.setDescricao("Candidato A");
		listaCandidatos.add(cand);
		cand = new Candidato();
		cand.setId(2);
		cand.setIdVotacao(1);
		cand.setDescricao("Candidato B");
		listaCandidatos.add(cand);
		v.setPeriodo(new Periodo(dataCorrente, proxDate, "dd/MM/yyyy"));
		v.setCandidatos(listaCandidatos);
		v.setDescricao("Votacao Arte Moderda");
		v.setEleitorado(UsuarioDaoMemory.listaUsuarios);
		listaVotacoes.add(v);
		v = new Votacao();
		v.setId(2);
		
		listaCandidatos = new ArrayList<Candidato>();
		cand = new Candidato();
		cand.setId(1);
		cand.setIdVotacao(2);
		cand.setDescricao("Candidato Alfa");
		listaCandidatos.add(cand);
		cand = new Candidato();
		cand.setId(2);
		cand.setIdVotacao(2);
		cand.setDescricao("Candidato Beta");
		listaCandidatos.add(cand);
		v.setCandidatos(listaCandidatos);
		v.setPeriodo(new Periodo(dataCorrente, proxDate, "dd/MM/yyyy"));
		v.setDescricao("Votacao Arte Barroca");
		v.setEleitorado(UsuarioDaoMemory.listaUsuarios);
		listaVotacoes.add(v);
	}

	/*
	 * (non-Javadoc)
	 * @see votacao.dao.VotacaoDao#criar(votacao.bean.Votacao)
	 */
	@Override
	public void criar(Votacao votacao) {
		int max = 0;
		for (Votacao v : listaVotacoes) {
			if (max < v.getId()) {
				max = v.getId();
			}
		} 
		final int idVotacao = max + 1;
		votacao.setId(idVotacao);
		listaVotacoes.add(votacao);
		
		int idCandidato = 1;
		for (Candidato c : votacao.getCandidatos()) {
			c.setIdVotacao(idVotacao);
			c.setId(idCandidato++);
		}
	}
	
	@Override
	public void salvar(Votacao votacao) throws DaoException {
		Iterator<Votacao> it = listaVotacoes.iterator();
		while (it.hasNext()) {
			Votacao v = it.next();
			if (v.getId() == votacao.getId()) {
				it.remove();
			}
		}
		listaVotacoes.add(votacao);
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.VotacaoDao#buscarPorId(int)
	 */
	@Override
	public Votacao buscarPorId(int idVotacao) {
		for (Votacao v : listaVotacoes) {
			if (idVotacao == v.getId()) {
				return v;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.VotacaoDao#buscarVotacaoAberta()
	 */
	@Override
	public Votacao buscarVotacaoAberta() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see votacao.dao.VotacaoDao#terminarVotacao(votacao.bean.Votacao)
	 */
	@Override
	public void terminarVotacao(Votacao votacao) {
		
	}

	/* (non-Javadoc)
	 * @see votacao.dao.VotacaoDao#buscarPorUsuario(votacao.bean.Usuario)
	 */
	@Override
	public List<Votacao> buscarPorUsuario(Usuario user) {
		List<Votacao> votacoes = new ArrayList<Votacao>(); 
		for (Votacao v : listaVotacoes) {
			for (Usuario u : v.getEleitorado()) {
				if (u.getLogin().equals(u.getLogin())) {
					boolean contem = false;
					for (Votacao v2 : votacoes) {
						if (v2.getId() == v.getId()) {
							contem = true;
						}
					}
					if (!contem) {
						votacoes.add(v);
					}
				}
			}
		}
		return votacoes;
	}

	@Override
	public List<Votacao> buscarTodas() throws DaoException {
		return listaVotacoes;
	}
	
}
