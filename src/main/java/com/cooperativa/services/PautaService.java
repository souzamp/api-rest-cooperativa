package com.cooperativa.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperativa.domain.Pauta;
import com.cooperativa.repositories.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta findPauta(Integer id) {
		Optional<Pauta> findById = pautaRepository.findById(id);
		return findById.orElse(null);
	}

	public Pauta insertPauta(Pauta obj) {
		return pautaRepository.save(obj);
	}
}
