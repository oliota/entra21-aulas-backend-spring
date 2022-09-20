package br.com.entra21.backend.spring.projeto.model;

import javax.persistence.Column;
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
	
	@Column(name = "qtd_linguagem")
	private int qtdLing;
	
	private Boolean logico;
	
	public Programador() {
		super();
	}

	 
 

	public Programador(Integer id, String nome, int qtdLing, Boolean logico) {
		super();
		this.id = id;
		this.nome = nome;
		this.qtdLing = qtdLing;
		this.logico = logico;
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

	


	public int getQtdLing() {
		return qtdLing;
	}




	public void setQtdLing(int qtdLing) {
		this.qtdLing = qtdLing;
	}




	public Boolean getLogico() {
		return logico;
	}




	public Boolean isLogico() {
		return logico;
	}


	 
	
	
	

}
