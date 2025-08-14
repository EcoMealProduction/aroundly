package com.backend.in.mapper.request;

import com.backend.adapter.in.dto.request.IncidentRequestDto;
import com.backend.adapter.in.mapper.*;
import com.backend.adapter.in.mapper.request.IncidentMetadataRequestMapperImpl;
import com.backend.adapter.in.mapper.request.IncidentRequestMapper;
import com.backend.adapter.in.mapper.request.IncidentRequestMapperImpl;
import com.backend.adapter.in.mapper.request.LocationRequestMapperImpl;
import com.backend.domain.happening.Incident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.in.mapper.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    IncidentRequestMapperImpl.class,
    IncidentMetadataRequestMapperImpl.class,
    LocationRequestMapperImpl.class,
    MediaRefMapperImpl.class,
})
public class IncidentRequestMapperTest {

    @Autowired private IncidentRequestMapper mapper;

    @Test
    void testToDomain() {
        Incident incident = mapper.toDomain(incidentRequestDto);

        assertEquals(incident.title(), incidentRequestDto.title());
        assertEquals(incident.description(), incidentRequestDto.description());

        assertNotNull(incidentRequestDto.metadata());
        assertEquals(incident.metadata().location().latitude(), incidentRequestDto.metadata().location().latitude());
        assertEquals(incident.metadata().location().longitude(), incidentRequestDto.metadata().location().longitude());
        assertEquals(incident.metadata().location().address(), incidentRequestDto.metadata().location().address());
    }
}
