package com.vagadeestacionamento.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vagadeestacionamento.models.VagaEstacionamentoModel;

@Repository
public interface VagaEstacionamentoRepository extends JpaRepository<VagaEstacionamentoModel, UUID> {

  boolean existsByPlacaCarro(String placaCarro);

  boolean existsByNumeroVaga(String numeroVaga);

  boolean existsByApartamentoAndBloco(String apartamento, String bloco);

}
