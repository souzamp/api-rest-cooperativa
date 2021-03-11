package com.cooperativa.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperativa.domain.Pauta;
import com.cooperativa.repositories.PautaRepository;

@Service
public class PautaService {
	private static Logger logger = LoggerFactory.getLogger(PautaService.class);

	@Autowired
	private PautaRepository pautaRepository;

	public Optional<Pauta> find(Integer id) throws Exception {
		logger.debug("Find - begin.");
		Optional<Pauta> obj = pautaRepository.findById(id);
		return obj;
	}

	public Pauta insertPauta(Pauta obj) {
		logger.debug("InsertPauta - begin.");
		return pautaRepository.save(obj);
	}
}
