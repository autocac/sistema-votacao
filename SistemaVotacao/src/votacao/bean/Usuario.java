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
	private boolean trocarSenha;

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
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

	public boolean isTrocarSenha() {
		return trocarSenha;
	}

	public void setTrocarSenha(boolean trocarSenha) {
		this.trocarSenha = trocarSenha;
	}

	public boolean isSenhaOk(String senha) {
		//[TODO:Alterar antes de colocar em produ��o]
		return this.senha.equals(senha);
		//return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	
	

}
