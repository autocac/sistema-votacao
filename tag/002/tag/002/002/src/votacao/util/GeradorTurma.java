package votacao.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import votacao.bean.Turma;
import votacao.bean.Usuario;

public class GeradorTurma {

	private Turma turma;
	
	private int tamanhoSenha;
	
	private boolean trocarSenha;

	private static final char[] CARACTERES = "abcdefghijABCDEFGHIJ0123456789".toCharArray();
	
	private List<String> listaCaracteres;
	
	public GeradorTurma(int tamanhoSenha, boolean trocarSenha) {
		this.trocarSenha = trocarSenha;
		this.tamanhoSenha = tamanhoSenha;
		this.listaCaracteres = getListaCaracteres();
	}

	public Turma getTurma(String id, int numAlunos) {
		this.turma = new Turma(id, numAlunos);
		List<Usuario> eleitores = gerarEleitores();
		this.turma.setEleitores(eleitores);
		return this.turma;
	}
	
	private List<String> getListaCaracteres() {
		List<String> caracteres = new ArrayList<String>();
		for (char c : CARACTERES) {
			caracteres.add(c+"");
		}
		return caracteres;
	}

	private List<Usuario> gerarEleitores() {
		
		List<Usuario> eleitores = new ArrayList<Usuario>();
		
		int tamanho = (this.turma.getNumAlunos() + "").length();
		for (int i = 1; i <= this.turma.getNumAlunos(); i++) {
			String sufixo = String.format("%0" + tamanho + "d", i);
			
			Usuario eleitor = new Usuario();
			eleitor.setLogin(this.turma.getId() + sufixo);
			eleitor.setSenha(gerarSenha());
			eleitor.setTrocarSenha(this.trocarSenha);
			eleitor.setTipo(Usuario.Tipo.ELEITOR);
			eleitores.add(eleitor);
		}
		
		return eleitores;
	}

	private String gerarSenha() {
		Collections.shuffle(this.listaCaracteres);
		StringBuffer senha = new StringBuffer();
		for (int i = 0; i < this.tamanhoSenha;i++) {
			senha.append(this.listaCaracteres.get(i));
		}
		return senha.toString();
	}
}
