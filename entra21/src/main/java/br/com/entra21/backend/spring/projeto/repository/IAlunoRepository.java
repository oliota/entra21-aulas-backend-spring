package br.com.entra21.backend.spring.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.entra21.backend.spring.projeto.model.Aluno;
 

public interface IAlunoRepository extends JpaRepository<Aluno, Integer> {

}
