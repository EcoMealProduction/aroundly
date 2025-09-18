package com.backend.adapter.inbound.rest;

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
@RequestMapping("/api/v1/feed")
@Tag(name = "Feed", description = "Content feed endpoints for incidents and events")
@SecurityRequirement(name = "bearerAuth")
public class FeedController {

    @GetMapping
    @Operation(
            summary = "Get feed content",
            description = "Retrieves incidents and events for the user's personalized feed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feed content"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public Map<String, List<String>> findAllInGivenRange() {
        return Map.of("incidents", List.of("incident1", "incident2"));
    }
}
