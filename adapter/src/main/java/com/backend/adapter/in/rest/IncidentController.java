package com.backend.adapter.in.rest;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
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

  /**
   * Creates a new incident based on client-provided request data.
   *
   * Request data is converted from {@link IncidentRequestDto} to the
   * {@link Incident} domain model using the {@link IncidentRequestMapper}.
   * The domain object is then persisted via the {@link IncidentUseCase} and
   * returned to the client.
   *
   * @param incidentRequestDto the incident data provided by the client
   * @return the created {@code Incident} as JSON with HTTP status {@code 201 Created}
   */
  @PostMapping
  public ResponseEntity<Incident> create(@RequestBody IncidentRequestDto incidentRequestDto) {
    Incident domainIncident = requestMapper.toDomain(incidentRequestDto);
    Incident incident = incidentUseCase.create(domainIncident);

    return new ResponseEntity<>(incident, HttpStatus.CREATED);
  }

  /**
   * Retrieves incidents located within a specified radius from the given coordinates.
   *
   * Results are mapped to {@link IncidentPreviewResponseDto} objects to provide
   * a lightweight preview of each incident.
   *
   * @param latitude     the latitude of the search origin point
   * @param longitude    the longitude of the search origin point
   * @param radiusMeters maximum search distance in meters
   * @return list of incident previews matching the criteria, with HTTP status {@code 200 OK}
   */
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
