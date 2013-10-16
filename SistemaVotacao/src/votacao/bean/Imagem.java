package votacao.bean;

import java.util.Arrays;

public class Imagem {

	private String nome;
	private String contentType;
	private byte[] bytes;
	public Imagem() {
	}
	public Imagem(String nome, String contentType, byte[] bytes) {
		super();
		this.nome = nome;
		this.contentType = contentType;
		this.bytes = bytes;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	@Override
	public String toString() {
		return "Imagem [nome=" + nome + ", contentType=" + contentType
				+ ", bytes=[" + (bytes.length > 0 ? bytes[0] : "") + "] ]";
	}
}
