package br.com.entra21.backend.spring.projeto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "programador")
public class Programador extends MaturidadeNivel3Richardson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private int qtdLinguagem;
	
	public Programador() {
		super();
	}

	public Programador(Integer id, String nome, int qtdLinguagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.qtdLinguagem = qtdLinguagem;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtdLinguagem() {
		return qtdLinguagem;
	}

	public void setQtdLinguagem(int qtdLinguagem) {
		this.qtdLinguagem = qtdLinguagem;
	}
	
	
	
	

}
