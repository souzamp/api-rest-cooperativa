package com.cooperativa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cooperativa.domain.Votacao;

@Repository("votacaoRepository")
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

	@Query("SELECT v FROM Votacao v WHERE v.cpfAssociado = ?1")
	Optional<Votacao> findByCpf(String cpfAssociado);
	
	@Query("SELECT v FROM Votacao v WHERE v.idPauta = ?1")
	List<Votacao> findResultadoPauta(Integer id);
}
