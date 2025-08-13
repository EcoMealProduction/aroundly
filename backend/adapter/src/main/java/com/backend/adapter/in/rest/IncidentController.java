package com.backend.adapter.in.rest;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.in.dto.response.incident.IncidentPreviewResponseDto;
import com.backend.adapter.in.mapper.request.IncidentRequestMapper;
import com.backend.adapter.in.mapper.response.IncidentDetailedResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapper;
import com.backend.domain.happening.Incident;
import com.backend.port.in.IncidentUseCase;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

  private final IncidentUseCase incidentUseCase;
  private final IncidentDetailedResponseMapper detailedResponseMapper;
  private final IncidentPreviewResponseMapper previewResponseMapper;
  private final IncidentRequestMapper requestMapper;

  public IncidentController(IncidentUseCase incidentUseCase,
      IncidentDetailedResponseMapper detailedResponseMapper,
      IncidentPreviewResponseMapper previewResponseMapper,
      IncidentRequestMapper requestMapper) {

    this.incidentUseCase = incidentUseCase;
    this.detailedResponseMapper = detailedResponseMapper;
    this.previewResponseMapper = previewResponseMapper;
    this.requestMapper = requestMapper;
  }

  @PostMapping
  public ResponseEntity<Incident> create(@RequestBody IncidentRequestDto incidentRequestDto) {
    Incident domainIncident = requestMapper.toDomain(incidentRequestDto);
    Incident incident = incidentUseCase.create(domainIncident);

    return new ResponseEntity<>(incident, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<IncidentPreviewResponseDto>> findNearbyIncidents(
      @RequestParam("lat") double latitude,
      @RequestParam("lon") double longitude,
      @RequestParam("radiusMeters") double radiusMeters) {

    List<Incident> results = incidentUseCase.findAllInGivenRange(latitude, longitude, radiusMeters);
    List<IncidentPreviewResponseDto> responseDtos = results.stream()
        .map(previewResponseMapper::toDto)
        .toList();

    return ResponseEntity.ok(responseDtos);
  }

}
