package com.cooperativa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperativa.domain.ResponseResultado;
import com.cooperativa.domain.Votacao;
import com.cooperativa.repositories.VotacaoRepository;

@Service
public class VotacaoService {

	@Autowired
	private VotacaoRepository votacaoRepository;

	public Boolean insertVoto(Votacao obj) {
		Boolean flagCpf = true;
		Optional<Votacao> votacao = votacaoRepository.findByCpf(obj.getCpfAssociado());
		if (!votacao.isPresent()) {
			votacaoRepository.save(obj);
			flagCpf = false;
		}
		return flagCpf;
	}

	public ResponseResultado findResuldo(Integer id) {
		List<Votacao> votacao = votacaoRepository.findResultadoPauta(id);
		int votoNao = 0;
		int votoSim = 0;
		int totalVotos = 0;

		if (!votacao.isEmpty()) {
			for (Votacao votacao2 : votacao) {
				if (votacao2.getVoto().equals("Não")) {
					votoNao += 1;
				} else if (votacao2.getVoto().equals("Sim")) {
					votoSim += 1;
				}
				totalVotos += 1;
			}

			ResponseResultado responseResultado = new ResponseResultado();
			responseResultado.setNomePauta("Teste");
			responseResultado.setTotalVotos(String.valueOf(totalVotos));
			responseResultado.setVotosSim(String.valueOf(votoSim));
			responseResultado.setVotosNao(String.valueOf(votoNao));
			return responseResultado;
		}

		return null;
	}
}
