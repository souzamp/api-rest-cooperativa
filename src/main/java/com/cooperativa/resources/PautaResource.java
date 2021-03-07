package com.cooperativa.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativa.domain.Pauta;
import com.cooperativa.services.PautaService;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {

	@Autowired
	private PautaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable("id") Integer id) {
		Pauta pauta = service.findPauta(id);
		return ResponseEntity.ok().body(pauta);
	}
}
