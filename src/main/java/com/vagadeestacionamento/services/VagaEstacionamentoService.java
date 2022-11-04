package com.vagadeestacionamento.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.vagadeestacionamento.models.VagaEstacionamentoModel;
import com.vagadeestacionamento.repositories.VagaEstacionamentoRepository;

@Service
public class VagaEstacionamentoService {

  final VagaEstacionamentoRepository vagaEstacionamentoRepository;

  public VagaEstacionamentoService(VagaEstacionamentoRepository vagaEstacionamentoRepository) {
    this.vagaEstacionamentoRepository = vagaEstacionamentoRepository;
  }

  @Transactional
  public VagaEstacionamentoModel save(VagaEstacionamentoModel vagaEstacionamentoModel) {
    return vagaEstacionamentoRepository.save(vagaEstacionamentoModel);
  }

  public boolean existsByPlacaCarro(String placaCarro) {
    return vagaEstacionamentoRepository.existsByPlacaCarro(placaCarro);
  }

  public boolean existsByNumeroVaga(String numeroVaga) {
    return vagaEstacionamentoRepository.existsByNumeroVaga(numeroVaga);
  }

  public boolean existsByApartamentoAndBloco(String apartamento, String bloco) {
    return vagaEstacionamentoRepository.existsByApartamentoAndBloco(apartamento, bloco);
  }

  public Optional<VagaEstacionamentoModel> findById(UUID id) {
    return vagaEstacionamentoRepository.findById(id);
  }

  @Transactional
  public void delete(VagaEstacionamentoModel vagaEstacionamentoModel) {
    vagaEstacionamentoRepository.delete(vagaEstacionamentoModel);
  }

  public Page<VagaEstacionamentoModel> findAll(org.springframework.data.domain.Pageable pageable) {
    return vagaEstacionamentoRepository.findAll(pageable);
  }

}
