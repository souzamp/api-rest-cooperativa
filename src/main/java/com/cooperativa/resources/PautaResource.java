package com.cooperativa.resources;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.Response;
import com.cooperativa.domain.ResponseResultado;
import com.cooperativa.domain.Votacao;
import com.cooperativa.resources.exception.StandardError;
import com.cooperativa.services.PautaService;
import com.cooperativa.services.VotacaoService;

@RestController
@RequestMapping(value = "/pautas")
public class PautaResource {
	private static Logger logger = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private PautaService servicePauta;

	private VotacaoService serviceVotacao;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findPautaById(@PathVariable("id") Integer id) {
		logger.debug("FindPautaById - begin.");

		try {
			Optional<Pauta> obj = servicePauta.find(id);
			if (!obj.isPresent()) {
				StandardError err = new StandardError(System.currentTimeMillis(), 404, "Not Found",
						"O servidor não pode encontrar o recurso solicitado.", null);

				logger.debug("Not Found - O servidor não pode encontrar o recurso solicitado.");

				return ResponseEntity.status(404).body(err);
			} else {
				return ResponseEntity.ok().body(obj);
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), 500, "Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(500).body(err);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody Pauta obj) {
		logger.info("Criando uma nova Pauta.");

		try {
			if (obj == null) {
				StandardError err = new StandardError(System.currentTimeMillis(), 400, "Bad Request",
						"Sintaxe inválida.", null);

				logger.debug("Bad Request - Sintaxe inválida, body null.");

				return ResponseEntity.status(400).body(err);
			} else {
				obj = servicePauta.insertPauta(obj);
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
						.toUri();

				Response response = new Response();
				response.setMessage("Pauta criada com sucesso!");
				return ResponseEntity.created(uri).body(response);
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), 500, "Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(500).body(err);
		}
	}

	@RequestMapping(value = "/{votacao}", method = RequestMethod.POST)
	public ResponseEntity<?> insertVotacao(@RequestBody Votacao obj) {
		logger.debug("InsertVotacao - begin.");
		try {
			ResponseEntity<?> response = serviceVotacao.insertVoto(obj);

			if (response.getStatusCodeValue() == 200 | response.getStatusCodeValue() == 202) {
				return ResponseEntity.status(200).body(response.getBody());
			} else {
				return ResponseEntity.status(500).body(response.getBody());
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), 500, "Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(500).body(err);
		}
	}

	@RequestMapping(value = "/resultadoVotacao/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> resultPauta(@PathVariable Integer id) {
		logger.debug("ResultPauta - begin");

		ResponseResultado findResuldo = serviceVotacao.findResuldo(id);
		return ResponseEntity.ok().body(findResuldo);
	}
}
