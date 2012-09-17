package votacao.bean;

public class Candidato {

	private int idVotacao;
	private int id;
	private String descricao;
	private int numeroVotos = 0;
	
	public int getIdVotacao() {
		return idVotacao;
	}

	public void setIdVotacao(int idVotacao) {
		this.idVotacao = idVotacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getNumeroVotos() {
		return numeroVotos;
	}
	
	public void setNumeroVotos(int numeroVotos) {
		this.numeroVotos = numeroVotos;
	}

	public void receberVoto() {
		this.numeroVotos++;
	}
}
