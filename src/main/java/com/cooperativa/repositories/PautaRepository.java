package com.cooperativa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooperativa.domain.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer>{

}
