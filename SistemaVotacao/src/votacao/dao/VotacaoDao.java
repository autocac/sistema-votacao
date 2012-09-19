package votacao.dao;

import java.util.List;

import votacao.bean.Usuario;
import votacao.bean.Votacao;

public interface VotacaoDao {

	public abstract void criar(Votacao votacao);

	public abstract Votacao buscarPorId(int idVotacao);

	public abstract Votacao buscarVotacaoAberta();

	public abstract void terminarVotacao(Votacao votacao);

	public abstract List<Votacao> buscarPorUsuario(Usuario user);

}