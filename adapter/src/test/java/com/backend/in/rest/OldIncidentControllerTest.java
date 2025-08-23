package com.backend.in.rest;

import static com.backend.in.mapper.MapperFixtures.DOMAIN_OLD_INCIDENT_FOR_CONTROLLER;
import static com.backend.in.mapper.MapperFixtures.incidentPreviewResponseDto;
import static com.backend.in.mapper.MapperFixtures.incidentRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.mapper.request.IncidentRequestMapper;
import com.backend.adapter.in.mapper.response.IncidentDetailedResponseMapper;
import com.backend.adapter.in.mapper.response.IncidentPreviewResponseMapper;
import com.backend.adapter.in.rest.IncidentController;
import com.backend.domain.happening.old.OldIncident;
import com.backend.port.inbound.IncidentUseCase;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class OldIncidentControllerTest {

  @Mock private IncidentUseCase incidentUseCase;
  @Mock private IncidentDetailedResponseMapper detailedResponseMapper;
  @Mock private IncidentPreviewResponseMapper previewResponseMapper;
  @Mock private IncidentRequestMapper requestMapper;
  @InjectMocks private IncidentController controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void createIncident() throws Exception {
    when(requestMapper.toDomain(any(IncidentRequestDto.class))).thenReturn(
        DOMAIN_OLD_INCIDENT_FOR_CONTROLLER);
    when(incidentUseCase.create(DOMAIN_OLD_INCIDENT_FOR_CONTROLLER)).thenReturn(
        DOMAIN_OLD_INCIDENT_FOR_CONTROLLER);

    ResponseEntity<OldIncident> response = controller.create(incidentRequestDto);

    System.out.println(response.getBody());
    String json = """
        {
          "title": "Valid Incident",
          "description": "Valid long oldIncident description",
          "comments": [],
          "metadata": {
            "actor": {
              "id": {
                "id": 1
              },
              "username": "vanea",
              "role": ["USER"]
            },
            "oldLocation": {
              "latitude": 47.0101,
              "longitude": 28.8576,
              "address": "Strada Test, Chisinau"
            },
            "media": [
              {
                "kind": "IMAGE",
                "contentType": "content-type",
                "uri": "/path/"
              }
            ],
            "createdAt": "2025-08-13T19:02:15.670967979",
            "expirationTime": "2026-01-01T13:00"
          },
          "sentimentEngagement": {
            "likes": 0,
            "dislikes": 0
          },
          "engagementStats": {
            "confirms": 0,
            "denies": 0,
            "consecutiveDenies": 0
          }
        }
      """;

    mockMvc.perform(
        post("/incidents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
    ).andExpect(status().isCreated());

  }

  @Test
  void testFindAllInGivenRange() throws Exception {
    double lat = 47.0101;
    double lon = 28.8576;
    double radius = 2000.0;

    when(incidentUseCase.findAllInGivenRange(eq(lat), eq(lon), eq(radius)))
        .thenReturn(List.of(DOMAIN_OLD_INCIDENT_FOR_CONTROLLER));
    when(previewResponseMapper.toDto(any(OldIncident.class))).thenReturn(incidentPreviewResponseDto);

    mockMvc.perform(get("/incidents")
            .param("lat", String.valueOf(lat))
            .param("lon", String.valueOf(lon))
            .param("radiusMeters", String.valueOf(radius)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("Test Incident Title"));
  }
}
