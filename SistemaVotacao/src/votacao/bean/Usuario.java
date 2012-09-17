package votacao.bean;

import votacao.bean.Usuario.Tipo;

public class Usuario {

	public enum Tipo {
		ELEITOR('E'),
		ADMINISTRADOR('A');
		
		private char codigo;

		private Tipo(char codigo) {
			this.codigo = codigo;
		}
		
		public char getCodigo() {
			return codigo;
		}

		public static Tipo valueOf(char codigo) {
			for (Tipo t : Tipo.values()) {
				if (t.codigo == codigo) {
					return t;
				}
			}
			return null;
		}
	}
	
	/**
	 * login eh chave
	 */
	private String login;
	private String senha;
	private Tipo tipo;
	private String nome;

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setTipo(char codigo) {
		this.tipo = Tipo.valueOf(codigo);
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isSenhaOk(String senha) {
		//[TODO:Alterar antes de colocar em produ��o]
		//return this.senha.equals(senha);
		return true;
	}

}
