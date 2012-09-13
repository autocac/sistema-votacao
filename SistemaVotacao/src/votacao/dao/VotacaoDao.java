package votacao.dao;

import java.util.ArrayList;
import java.util.List;

import votacao.bean.Candidato;
import votacao.bean.Usuario;
import votacao.bean.Votacao;


public class VotacaoDao {

	static List<Votacao> listaVotacoes;
	
	static {
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
		v.setCandidatos(listaCandidatos);
		v.setDescricao("Votacao Arte Moderda");
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
		
		v.setDescricao("Votacao Arte Barroca");
		listaVotacoes.add(v);
	}

	public Votacao criarVotacao() {
		return null;
	}
	
	public Votacao buscarPorId(int idVotacao) {
		for (Votacao v : listaVotacoes) {
			if (idVotacao == v.getId()) {
				return v;
			}
		}
		return null;
	}
	
	public Votacao buscarVotacaoAberta() {
		return null;
	}
	
	public void terminarVotacao(Votacao votacao) {
		
	}

	public List<Votacao> buscarPorUsuario(Usuario user) {
		return listaVotacoes;
	}
}
