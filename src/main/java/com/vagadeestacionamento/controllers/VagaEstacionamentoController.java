package com.vagadeestacionamento.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vagadeestacionamento.dtos.VagaEstacionamentoDto;
import com.vagadeestacionamento.models.VagaEstacionamentoModel;
import com.vagadeestacionamento.services.VagaEstacionamentoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("vaga-estacionamento")
public class VagaEstacionamentoController {

  final VagaEstacionamentoService vagaEstacionamentoService;

  public VagaEstacionamentoController(VagaEstacionamentoService vagaEstacionamentoService) {
    this.vagaEstacionamentoService = vagaEstacionamentoService;
  }

  @PostMapping
  public ResponseEntity<Object> salvarVagas(@RequestBody @Valid VagaEstacionamentoDto vagaEstacionamentoDto) {
    if (vagaEstacionamentoService.existsByPlacaCarro(vagaEstacionamentoDto.getPlacaCarro())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Placa de carro já existe, está em uso!");
    }
    if (vagaEstacionamentoService.existsByNumeroVaga(vagaEstacionamentoDto.getNumeroVaga())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A vaga de estacionamento já está em uso!");
    }
    if (vagaEstacionamentoService.existsByApartamentoAndBloco(vagaEstacionamentoDto.getApartamento(),
        vagaEstacionamentoDto.getBloco())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Conflito: Vaga de Estacionamento já registrada para este apartamento/bloco!");
    }
    var vagaEstacionamentoModel = new VagaEstacionamentoModel();
    BeanUtils.copyProperties(vagaEstacionamentoDto, vagaEstacionamentoModel);
    vagaEstacionamentoModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
    return ResponseEntity.status(HttpStatus.CREATED).body(vagaEstacionamentoService.save(vagaEstacionamentoModel));
  }

  @GetMapping
  public ResponseEntity<Page<VagaEstacionamentoModel>> getAllParkingSpots(
      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOneVagaEstacionamento(@PathVariable(value = "id") UUID id) {
    Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.findById(id);
    if (!vagaEstacionamentoModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoModelOptional.get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
    Optional<VagaEstacionamentoModel> vagaEstacionamentoModelOptional = vagaEstacionamentoService.findById(id);
    if (!vagaEstacionamentoModelOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada.");
    }
    vagaEstacionamentoService.delete(vagaEstacionamentoModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK).body("Vaga de Estacionamento removida com sucesso!.");
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> atualizarVagas(@PathVariable(value = "id") UUID id,
      @RequestBody @Valid VagaEstacionamentoDto vagaEstacionamentoDto) {
    Optional<VagaEstacionamentoModel> vagaEstacionamentoOptional = vagaEstacionamentoService.findById(id);
    if (!vagaEstacionamentoOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
    }
    var vagaEstacionamentoModel = new VagaEstacionamentoModel();
    BeanUtils.copyProperties(vagaEstacionamentoDto, vagaEstacionamentoModel);
    vagaEstacionamentoModel.setId(vagaEstacionamentoOptional.get().getId());
    vagaEstacionamentoModel.setDataRegistro(vagaEstacionamentoOptional.get().getDataRegistro());
    return ResponseEntity.status(HttpStatus.OK).body(vagaEstacionamentoService.save(vagaEstacionamentoModel));
  }

}
