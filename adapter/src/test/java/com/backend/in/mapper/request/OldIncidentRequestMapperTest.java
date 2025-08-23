package com.backend.in.mapper.request;

import com.backend.adapter.in.mapper.*;
import com.backend.adapter.in.mapper.request.IncidentMetadataRequestMapperImpl;
import com.backend.adapter.in.mapper.request.IncidentRequestMapper;
import com.backend.adapter.in.mapper.request.IncidentRequestMapperImpl;
import com.backend.adapter.in.mapper.request.LocationRequestMapperImpl;
import com.backend.domain.happening.old.OldIncident;
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
public class OldIncidentRequestMapperTest {

    @Autowired private IncidentRequestMapper mapper;

    @Test
    void testToDomain() {
        OldIncident oldIncident = mapper.toDomain(incidentRequestDto);

        assertEquals(oldIncident.title(), incidentRequestDto.title());
        assertEquals(oldIncident.description(), incidentRequestDto.description());

        assertNotNull(incidentRequestDto.metadata());
        assertEquals(oldIncident.metadata().oldLocation().latitude(), incidentRequestDto.metadata().location().latitude());
        assertEquals(
            oldIncident.metadata().oldLocation().longitude(), incidentRequestDto.metadata().location().longitude());
        assertEquals(oldIncident.metadata().oldLocation().address(), incidentRequestDto.metadata().location().address());
    }
}
