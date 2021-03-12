package com.cooperativa.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.Votacao;
import com.cooperativa.resources.exception.StandardError;
import com.cooperativa.services.PautaService;
import com.cooperativa.services.VotacaoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {
	private static Logger logger = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private PautaService servicePauta;
	@Autowired
	private VotacaoService serviceVotacao;

	@ApiOperation("Busca por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findPautaById(@PathVariable("id") Integer id) {
		logger.debug("FindPautaById - begin.");

		try {
			ResponseEntity<?> response = servicePauta.find(id);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}

	}

	@ApiOperation("Cria uma pauta")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody Pauta obj) {
		logger.info("Criando uma nova Pauta.");

		try {
			logger.info("Chamando p método para criar uma pauta.");

			ResponseEntity<?> response = servicePauta.insertPauta(obj);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}

	@ApiOperation("Insere o voto")
	@RequestMapping(value = "/{votacao}", method = RequestMethod.POST)
	public ResponseEntity<?> insertVotacao(@RequestBody Votacao obj) {
		logger.debug("InsertVotacao - begin.");
		try {
			logger.info("Chamando p método para inserir o voto.");

			ResponseEntity<?> response = serviceVotacao.insertVoto(obj);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}

	@ApiOperation("Busca resultado por id")
	@RequestMapping(value = "/resultadoVotacao/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> resultPauta(@PathVariable Integer id) {
		logger.debug("ResultPauta - begin");

		try {
			ResponseEntity<?> response = serviceVotacao.findResultado(id);
			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}
}
