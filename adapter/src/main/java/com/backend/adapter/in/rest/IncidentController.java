package com.backend.adapter.in.rest;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.mapper.IncidentMapper;
import com.backend.adapter.in.mapper.assembler.IncidentDtoAssembler;
import com.backend.domain.happening.Incident;
import com.backend.port.inbound.IncidentUseCase;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing incidents reported by users.
 *
 * Provides endpoints for creating new incidents and retrieving existing
 * incidents within a given distance from a geographic location. This controller
 * delegates business logic to the {@link IncidentUseCase} and uses mappers to
 * translate between domain models and API DTOs.
 *
 */
@RestController
@RequestMapping("/incidents")
public class IncidentController {

  private final IncidentUseCase incidentUseCase;
  private final IncidentDtoAssembler assembler;
  private final IncidentMapper mapper;

  public IncidentController(IncidentUseCase incidentUseCase,
      IncidentDtoAssembler assembler,
      IncidentMapper mapper) {

    this.incidentUseCase = incidentUseCase;
    this.mapper = mapper;
    this.assembler = assembler;
  }

  @PostMapping
  public ResponseEntity<IncidentDetailedResponseDto> create(
      @RequestBody IncidentRequestDto incidentRequestDto) {

    CreateIncidentCommand createIncidentCommand = mapper.toCreateIncidentCommand(incidentRequestDto);
    Incident incident = incidentUseCase.create(createIncidentCommand);
    IncidentDetailedResponseDto incidentDetailedResponseDto = assembler.toDetailedDto(incident);

    return new ResponseEntity<>(incidentDetailedResponseDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<IncidentPreviewResponseDto>> findNearbyIncidents(
      @RequestParam("lat") double latitude,
      @RequestParam("lon") double longitude,
      @RequestParam("radiusMeters") double radiusMeters) {

    RadiusCommand radiusCommand = new RadiusCommand(latitude, longitude, radiusMeters);

    List<Incident> results = incidentUseCase.findAllInGivenRange(radiusCommand);
    List<IncidentPreviewResponseDto> responseDtos = results.stream()
        .map(mapper::toIncidentPreviewResponseDto)
        .toList();

    return ResponseEntity.ok(responseDtos);
  }

}
