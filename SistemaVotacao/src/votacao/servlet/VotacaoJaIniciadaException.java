package votacao.servlet;

import votacao.bean.Votacao;
import votacao.exception.BaseException;

public class VotacaoJaIniciadaException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Votacao votacao;
	private int totalVotos;
	
	public VotacaoJaIniciadaException(
			Votacao votacao, 
			int totalVotos) {
		this.votacao = votacao;
		this.totalVotos = totalVotos;
	}

	public Votacao getVotacao() {
		return votacao;
	}



	public int getTotalVotos() {
		return totalVotos;
	}



	@Override
	public String getMessage() {
		return String.format("Esta votação [%s] já recebeu %d voto(s) e nao pode ser mais alterada", 
				votacao.getId() + "-'" + votacao.getDescricao() +"'",
				totalVotos);
	}
}
