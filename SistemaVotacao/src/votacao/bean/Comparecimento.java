package votacao.bean;

public class Comparecimento {

	private int idVotacao;
	private int idCandidato;
	private String loginUsuario;
	
	public int getIdVotacao() {
		return idVotacao;
	}
	public void setIdVotacao(int idVotacao) {
		this.idVotacao = idVotacao;
	}
	public int getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(int idCandidato) {
		this.idCandidato = idCandidato;
	}
	public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
}
