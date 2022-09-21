package br.com.entra21.backend.spring.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 

import br.com.entra21.backend.spring.projeto.model.Programador;

public interface IProgramadorRepository extends JpaRepository<Programador, Integer> {

}
