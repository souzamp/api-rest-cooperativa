package com.cooperativa.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.ResponseResultado;
import com.cooperativa.domain.Votacao;
import com.cooperativa.services.PautaService;
import com.cooperativa.services.VotacaoService;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {

	@Autowired
	private PautaService servicePauta;
	@Autowired
	private VotacaoService serviceVotacao;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable("id") Integer id) {
		Pauta pauta = servicePauta.findPauta(id);
		return ResponseEntity.ok().body(pauta);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody Pauta obj) {
		obj = servicePauta.insertPauta(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{votacao}", method = RequestMethod.POST)
	public ResponseEntity<?> insertVotacao(@RequestBody Votacao obj) {
		Boolean flagReturn = serviceVotacao.insertVoto(obj);
		if (!flagReturn) {
			return ResponseEntity.status(201).body(null);
		}
		return ResponseEntity.status(404).build();
	}

	@RequestMapping(value = "/resultadoVotacao/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> resultPauta(@PathVariable Integer id) {
		ResponseResultado findResuldo = serviceVotacao.findResuldo(id);
		return ResponseEntity.ok().body(findResuldo);
	}
}
