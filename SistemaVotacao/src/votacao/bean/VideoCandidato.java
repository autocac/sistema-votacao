package votacao.bean;

import java.io.File;

public class VideoCandidato extends Candidato {

	private File arquivo;

	private String titulo;

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
