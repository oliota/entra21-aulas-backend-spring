package br.com.entra21.backend.spring.projeto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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

import br.com.entra21.backend.spring.projeto.model.ItemNivel3;
import br.com.entra21.backend.spring.projeto.model.Programador;
import br.com.entra21.backend.spring.projeto.repository.IProgramadorRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/programadores")
public class ProgramadorController {

	private final String PATH = "localhost:8080/programadores";

	@Autowired
	private IProgramadorRepository programadorRepository;

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public List<Programador> listAll() {
		List<Programador> response = programadorRepository.findAll();

		response.forEach(programador -> {
			setMaturidadeNivel3(programador);
		});

		return response;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Programador> get(@PathVariable("id") int id) {
		List<Programador> response = programadorRepository.findById(id).stream().toList();

		response.forEach(programador -> {
			setMaturidadeNivel3(programador);
		});

		return response;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Programador create(@RequestBody Programador novoProgramador) {

		Programador response = programadorRepository.save(novoProgramador);

		return response;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Programador> update(@PathVariable("id") int id,
			@RequestBody Programador programadorEditado) {
		 
		Programador atual = programadorRepository.findById(id).get();
		atual.setNome(programadorEditado.getNome());
		atual.setQtdLinguagem(programadorEditado.getQtdLinguagem());
		programadorRepository.save(atual);

		return programadorRepository.findById(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		programadorRepository.deleteById(id);
		return !programadorRepository.existsById(id);
	}

	private void setMaturidadeNivel3(Programador programador) {

		ArrayList<String> headers = new ArrayList();
		headers.add("Accept : application/json");
		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			programador.setLinks(null);
			String nomeAtual = programador.getNome();
			programador.setNome("Nome diferente");
			String jsonUpdate = mapper.writeValueAsString(programador);
			programador.setNome(nomeAtual);
			programador.setId(null);
			String jsonCreate = mapper.writeValueAsString(programador);
			programador.setLinks(new ArrayList<>());
			programador.getLinks().add(new ItemNivel3("GET", PATH, null, null));
			programador.getLinks().add(new ItemNivel3("GET", PATH + "/" + programador.getId(), null, null));
			programador.getLinks().add(new ItemNivel3("POST", PATH, headers, jsonCreate));
			programador.getLinks().add(new ItemNivel3("PUT", PATH + "/" + programador.getId(), headers, jsonUpdate));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

}
