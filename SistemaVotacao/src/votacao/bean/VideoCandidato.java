package votacao.bean;

import java.io.File;

public class VideoCandidato extends Candidato {

	private File arquivo;

	private String titulo;
	
	private File arquivoAsx;

	public File getArquivo() {
		return arquivo;
	}

	public void setArquivo(File arquivo) {
		this.arquivo = arquivo;
	}
	
	public File getArquivoAsx() {
		return arquivoAsx;
	}

	public void setArquivoAsx(File arquivoAsx) {
		this.arquivoAsx = arquivoAsx;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
