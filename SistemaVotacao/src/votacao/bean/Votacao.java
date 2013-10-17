package votacao.bean;

import java.util.Date;
import java.util.List;

public class Votacao {

	private int id;
	private String descricao;
	private Periodo periodo;
	private List<Candidato> candidatos;
	private List<Usuario> eleitorado;
	private Usuario administrador;
	private boolean secreta;
	private Imagem fundo;
	
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
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	public List<Candidato> getCandidatos() {
		return candidatos;
	}
	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	public List<Usuario> getEleitorado() {
		return eleitorado;
	}
	public void setEleitorado(List<Usuario> eleitorado) {
		this.eleitorado = eleitorado;
	}
	public Usuario getAdministrador() {
		return administrador;
	}
	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}
	public boolean isEncerrada() {
		return !this.periodo.dentroDoPeriodo(this.periodo.getDataNormalizada(new Date()));
	}
	public void setSecreta(boolean secreta) {
		this.secreta = secreta;
	}
	public boolean isSecreta() {
		return secreta;
	}
	public Imagem getFundo() {
		return fundo;
	}
	public void setFundo(Imagem fundo) {
		if ((fundo != null )
				&& ((this.fundo == null) 
						|| (fundo.getNome() != null && fundo.getNome().trim().length() > 0 ))) {
			this.fundo = fundo;
		}
	}
	public int getMaxIdCandidato() {
		int max = 0;
		for (Candidato c : getCandidatos()) {
			if (max < c.getId()) {
				max = c.getId();
			}
			
		}
		return max;
	}
}
