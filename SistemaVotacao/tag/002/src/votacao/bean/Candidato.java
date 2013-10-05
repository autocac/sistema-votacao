package votacao.bean;

public class Candidato {

	private int idVotacao;
	private int id;
	private String nome;
	private byte[] imagem;
	private String imageContentType;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
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
