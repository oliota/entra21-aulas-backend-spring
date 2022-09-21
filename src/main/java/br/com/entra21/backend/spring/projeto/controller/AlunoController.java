package br.com.entra21.backend.spring.projeto.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.entra21.backend.spring.projeto.model.Aluno;
import br.com.entra21.backend.spring.projeto.model.ItemNivel3;
import br.com.entra21.backend.spring.projeto.repository.IAlunoRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/alunos")
public class AlunoController {

	

	@Autowired
	private IAlunoRepository alunoRepository;

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<Aluno> listAll() {
		List<Aluno> response = alunoRepository.findAll();

		response.forEach(aluno -> {
			setMaturidadeNivel3(aluno);
		});

		return response;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Aluno> get(@PathVariable("id") int id) {
		List<Aluno> response = alunoRepository.findById(id).stream().toList();

		response.forEach(aluno -> {
			setMaturidadeNivel3(aluno);
		});

		return response;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Aluno create(@RequestBody Aluno novoAluno) {

		Aluno response = alunoRepository.save(novoAluno);
		setMaturidadeNivel3(response);
		return response;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Aluno update(@PathVariable("id") int id, @RequestBody Aluno alunoNovosDados) {

		Aluno response = alunoRepository.findById(id).get();
		response.setNome(alunoNovosDados.getNome());
		response.setIdade(alunoNovosDados.getIdade());
		response=alunoRepository.save(response);
		setMaturidadeNivel3(response);
		return response;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		alunoRepository.deleteById(id);
		return !alunoRepository.existsById(id);
	}

	private void setMaturidadeNivel3(Aluno aluno) {
		final String PATH = "localhost:8080/alunos";
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Accept : application/json");
		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {

			Aluno clone = mapper.readValue(mapper.writeValueAsString(aluno), Aluno.class);
			
			clone.setLinks(null);
			String nomeAtual = clone.getNome();
			clone.setNome("Nome diferente");
			String jsonUpdate = mapper.writeValueAsString(clone);
			
			clone.setNome(nomeAtual);
			clone.setId(null);
			String jsonCreate = mapper.writeValueAsString(clone);
			
			aluno.setLinks(new ArrayList<>());
			aluno.getLinks().add(new ItemNivel3("GET", PATH, null, null));
			aluno.getLinks().add(new ItemNivel3("GET", PATH + "/" + aluno.getId(), null, null));
			aluno.getLinks().add(new ItemNivel3("POST", PATH, headers, jsonCreate));
			aluno.getLinks().add(new ItemNivel3("PUT", PATH + "/" + aluno.getId(), headers, jsonUpdate));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}
