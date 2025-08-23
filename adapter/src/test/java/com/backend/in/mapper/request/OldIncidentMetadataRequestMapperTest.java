package com.backend.in.mapper.request;

import com.backend.adapter.in.mapper.MediaRefMapperImpl;
import com.backend.adapter.in.mapper.request.IncidentMetadataRequestMapper;
import com.backend.adapter.in.mapper.request.IncidentMetadataRequestMapperImpl;
import com.backend.adapter.in.mapper.request.LocationRequestMapperImpl;
import com.backend.domain.happening.metadata.IncidentMetadata;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.in.mapper.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    IncidentMetadataRequestMapperImpl.class,
    MediaRefMapperImpl.class,
    LocationRequestMapperImpl.class
})
public class OldIncidentMetadataRequestMapperTest {

    @Autowired
    private IncidentMetadataRequestMapper mapper;

    @Test
    void testToDomain() {
        IncidentMetadata domain = mapper.toDomain(incidentMetadataRequestDto);

        assertNull(domain.actor());
        assertEquals(domain.createdAt().getMinute(), LocalDateTime.now().getMinute());
        assertEquals(domain.oldLocation().latitude(), incidentMetadataRequestDto.location().latitude());
        assertEquals(domain.oldLocation().longitude(), incidentMetadataRequestDto.location().longitude());
        assertEquals(domain.oldLocation().address(), incidentMetadataRequestDto.location().address());
    }
}
