package com.backend.in;

import com.backend.happening.metadata.IncidentMetadata;
import com.backend.in.dto.shared.IncidentMetadataDto;
import com.backend.in.mapper.IncidentMetadataMapper;
import com.backend.in.mapper.IncidentMetadataMapperImpl;
import com.backend.in.mapper.LocationMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.in.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        IncidentMetadataMapperImpl.class,
        LocationMapperImpl.class
})
public class IncidentMetadataMapperTest {

    @Autowired
    private IncidentMetadataMapper mapper;

    @Test
    void testToDomain() {
        IncidentMetadata result = mapper.toDomain(incidentMetadataDto);

        assertEquals(incidentMetadataDto.authorUsername(), result.authorUsername());
        assertEquals(incidentMetadataDto.createdAt(), result.createdAt());
        assertEquals(incidentMetadataDto.expirationTime(), result.expirationTime());

        assertEquals(incidentMetadataDto.location().latitude(), result.location().latitude());
        assertEquals(incidentMetadataDto.location().longitude(), result.location().longitude());
        assertEquals(incidentMetadataDto.location().address(), result.location().address());
    }

    @Test
    void testToDto() {
        IncidentMetadataDto result = mapper.toDto(incidentMetadata);

        assertEquals(incidentMetadata.authorUsername(), result.authorUsername());
        assertEquals(incidentMetadata.createdAt(), result.createdAt());
        assertEquals(incidentMetadata.expirationTime(), result.expirationTime());

        assertEquals(incidentMetadata.location().latitude(), result.location().latitude());
        assertEquals(incidentMetadata.location().longitude(), result.location().longitude());
        assertEquals(incidentMetadata.location().address(), result.location().address());
    }

    @Test
    void testMetadataToIncidentMetadataDto_valid() {
        IncidentMetadataDto result = mapper.metadataToIncidentMetadataDto(incidentMetadata);
        assertEquals(incidentMetadata.authorUsername(), result.authorUsername());
    }
}
