package com.example.apisalasdeaula.repository;

import com.example.apisalasdeaula.model.sala.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    boolean existsByCodigo(String codigo);
}