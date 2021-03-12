package com.cooperativa.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooperativa.domain.Pauta;
import com.cooperativa.domain.Response;
import com.cooperativa.domain.ResponseResultado;
import com.cooperativa.domain.Votacao;
import com.cooperativa.repositories.PautaRepository;
import com.cooperativa.repositories.VotacaoRepository;
import com.cooperativa.resources.PautaResource;
import com.cooperativa.resources.exception.StandardError;

@Service
public class VotacaoService {
	private static Logger logger = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private VotacaoRepository votacaoRepository;
	@Autowired
	private PautaRepository pautaRepository;

	public ResponseEntity<?> insertVoto(Votacao obj) {
		logger.debug("InsertVoto - begin");
		try {
			if (obj.getCpfAssociado() != null && obj.getCpfAssociado().isEmpty() != true) {

				Optional<Votacao> votacao = votacaoRepository.findByCpf(obj.getCpfAssociado());
				Response response = new Response();
				if (!votacao.isPresent()) {
					votacaoRepository.save(obj);

					response.setMessage("Voto inserido com sucesso!");
					response.setStatus("OK");
					return ResponseEntity.status(HttpStatus.OK).body(response);
				} else {
					response.setMessage("Associado já votou!");
					response.setStatus("NOK");
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
				}
			} else {
				StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST, "Bad Request",
						"", null);

				logger.error("Bad Request.", err);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
			}
		} catch (Exception e) {
			StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Erro", "", null);

			logger.error("Internal Server Erro.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
		}
	}

	public ResponseResultado findResultado(Integer id) {
		logger.debug("FindResuldo - begin");

		List<Votacao> votacao = votacaoRepository.findResultadoPauta(id);
		int votoNao = 0;
		int votoSim = 0;
		int totalVotos = 0;

		if (!votacao.isEmpty()) {
			logger.debug("Contabilizando os votos.");

			for (Votacao votacao2 : votacao) {
				if (votacao2.getVoto().equals("Não")) {
					votoNao += 1;
				} else if (votacao2.getVoto().equals("Sim")) {
					votoSim += 1;
				}
				totalVotos += 1;
			}
			Optional<Pauta> pauta = pautaRepository.findById(id);

			logger.debug("Resultado da votação -  Nome da Pauta: " + pauta.get().getPauta() + " | total de votos: "
					+ totalVotos + " | votos sim: " + votoSim + " | votos não: " + votoNao);

			ResponseResultado responseResultado = new ResponseResultado();
			responseResultado.setNomePauta(pauta.get().getPauta());
			responseResultado.setTotalVotos(String.valueOf(totalVotos));
			responseResultado.setVotosSim(String.valueOf(votoSim));
			responseResultado.setVotosNao(String.valueOf(votoNao));
			return responseResultado;
		} else {
			logger.debug("Resultado não encontrado pelo Id passado.");
		}

		return null;
	}
}
