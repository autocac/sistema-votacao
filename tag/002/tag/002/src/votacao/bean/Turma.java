package votacao.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Turma {

	private String id;
	
	private int numAlunos;
	
	private List<Usuario> eleitores;
	
	/**
	 * @param id
	 * @param numAlunos
	 */
	public Turma(String id, int numAlunos) {
		this.id = id;
		this.numAlunos = numAlunos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumAlunos() {
		return numAlunos;
	}

	public void setNumAlunos(int numAlunos) {
		this.numAlunos = numAlunos;
	}

	public List<Usuario> getEleitores() {
		return eleitores;
	}

	public void setEleitores(List<Usuario> eleitores) {
		this.eleitores = eleitores;
	}
}
