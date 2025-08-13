package com.backend.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
@Tag(name = "Events", description = "Event management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class EventController {

    @GetMapping
    @Operation(
            summary = "Get all events in range",
            description = "Retrieves all events within a given geographic or time range"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved events"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public Map<String, List<String>> findAllInGivenRange() {
        return Map.of("events", List.of("event1", "event2"));
    }
}
