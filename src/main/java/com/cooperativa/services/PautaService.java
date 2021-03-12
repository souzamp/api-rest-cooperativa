package com.cooperativa.services;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.Response;
import com.cooperativa.repositories.PautaRepository;
import com.cooperativa.resources.exception.StandardError;

@Service
public class PautaService {
	private static Logger logger = LoggerFactory.getLogger(PautaService.class);

	@Autowired
	private PautaRepository pautaRepository;

	public ResponseEntity<?> find(Integer id) throws Exception {
		logger.debug("Find - begin.");

		try {
			Optional<Pauta> obj = pautaRepository.findById(id);
			if (!obj.isPresent()) {
				StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND, "Not Found",
						"O servidor não pode encontrar o recurso solicitado.", null);

				logger.debug("Not Found - O servidor não pode encontrar o recurso solicitado.");

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
			} else {
				return ResponseEntity.ok().body(obj);
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}

	public ResponseEntity<?> insertPauta(Pauta obj) {
		logger.debug("InsertPauta - begin.");
		try {
			if (obj.getPauta() == null | obj.getPauta().isEmpty() == true) {
				StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Bad Request",
						"Sintaxe inválida.", null);

				logger.debug("Bad Request - Sintaxe inválida, body null.");

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
			} else {
				obj = pautaRepository.save(obj);
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
						.toUri();

				Response response = new Response();
				response.setMessage("Pauta criada com sucesso!");
				response.setStatus("OK");
				return ResponseEntity.created(uri).body(response);
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}
}
