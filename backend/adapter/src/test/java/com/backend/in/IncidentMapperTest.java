package com.backend.in;

import com.backend.adapter.in.mapper.*;
import com.backend.domain.happening.Incident;
import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.dto.response.IncidentResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.in.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        IncidentMapperImpl.class,
        IncidentMetadataMapperImpl.class,
        LocationMapperImpl.class,
        IncidentEngagementStatsMapperImpl.class
})
public class IncidentMapperTest {

    @Autowired
    private IncidentMapper incidentMapper;

    @Test
    void testIncidentRequestDtoToIncident() {
        IncidentRequestDto dto = incidentRequestDto;
        Incident incident = incidentMapper.toIncident(dto);

        assertEquals(dto.title(), incident.title());
        assertEquals(dto.description(), incident.description());

        assertNotNull(incident.metadata());
        assertEquals(dto.incidentMetadata().authorUsername(), incident.metadata().authorUsername());
        assertEquals(dto.incidentMetadata().createdAt(), incident.metadata().createdAt());
        assertEquals(dto.incidentMetadata().expirationTime(), incident.metadata().expirationTime());

        assertEquals(dto.incidentMetadata().location().latitude(), incident.metadata().location().latitude());
        assertEquals(dto.incidentMetadata().location().longitude(), incident.metadata().location().longitude());
        assertEquals(dto.incidentMetadata().location().address(), incident.metadata().location().address());

        assertNull(incident.sentimentEngagement());
        assertTrue(incident.comments().isEmpty());
        assertNull(incident.incidentEngagementStats());
    }

    @Test
    void testIncidentToIncidentResponseDto() {
        Incident incident = domainIncident;
        IncidentResponseDto dto = incidentMapper.toIncidentDto(incident);

        assertEquals(incident.title(), dto.title());
        assertEquals(incident.description(), dto.description());

        assertNotNull(dto.incidentMetadata());
        assertEquals(incident.metadata().authorUsername(), dto.incidentMetadata().authorUsername());
        assertEquals(incident.metadata().createdAt(), dto.incidentMetadata().createdAt());
        assertEquals(incident.metadata().expirationTime(), dto.incidentMetadata().expirationTime());

        assertEquals(incident.metadata().location().latitude(), dto.incidentMetadata().location().latitude());
        assertEquals(incident.metadata().location().longitude(), dto.incidentMetadata().location().longitude());
        assertEquals(incident.metadata().location().address(), dto.incidentMetadata().location().address());

        assertNull(dto.sentiment());
        assertNull(dto.comments());

        assertNotNull(dto.engagementStats());
        assertEquals(incident.incidentEngagementStats().confirms(), dto.engagementStats().confirms());
        assertEquals(incident.incidentEngagementStats().denies(), dto.engagementStats().denies());
        assertEquals(incident.incidentEngagementStats().consecutiveDenies(), dto.engagementStats().consecutiveDenies());
    }
}
