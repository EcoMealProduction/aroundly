package com.backend.adapter.inbound.rest;

import com.backend.adapter.inbound.dto.request.IncidentRequestDto;
import com.backend.adapter.inbound.dto.response.incident.IncidentDetailedResponseDto;
import com.backend.adapter.inbound.mapper.IncidentMapper;
import com.backend.adapter.inbound.mapper.LocationMapper;
import com.backend.adapter.inbound.mapper.assembler.IncidentDtoAssembler;
import com.backend.adapter.inbound.rest.exception.incident.DuplicateIncidentException;
import com.backend.domain.happening.Incident;
import com.backend.port.inbound.IncidentUseCase;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/incidents")
public class TestController {

    private final IncidentUseCase incidentUseCase;
    private final IncidentDtoAssembler assembler;
    private final IncidentMapper incidentMapper;
    private final LocationMapper locationMapper;

    public TestController(
            IncidentUseCase incidentUseCase,
            IncidentDtoAssembler assembler,
            IncidentMapper incidentMapper,
            LocationMapper locationMapper) {

        this.incidentUseCase = incidentUseCase;
        this.incidentMapper = incidentMapper;
        this.assembler = assembler;
        this.locationMapper = locationMapper;
    }

    /**
     * Creates a new incident.
     *
     * @param incidentRequestDto DTO containing incident data
     * @return detailed DTO of the created incident
     */
    @PostMapping("/create")
    public ResponseEntity<IncidentDetailedResponseDto> create(
            @RequestBody @Valid IncidentRequestDto incidentRequestDto) {

        try {
            CreateIncidentCommand createIncidentCommand = incidentMapper
                    .toCreateIncidentCommand(incidentRequestDto);

            Incident incident = incidentUseCase.create(createIncidentCommand);
            IncidentDetailedResponseDto incidentDetailedResponseDto = assembler.toDetailedDto(incident);

            return new ResponseEntity<>(incidentDetailedResponseDto, HttpStatus.CREATED);

        } catch (DuplicateIncidentException e) {
            log.warn("Attempted to create duplicate incident: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ValidationException e) {
            log.warn("Invalid incident data: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
