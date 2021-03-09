package com.cooperativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cooperativa.domain.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {
}
