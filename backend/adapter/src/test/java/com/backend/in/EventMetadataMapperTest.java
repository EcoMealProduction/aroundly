package com.backend.in;

import com.backend.adapter.in.mapper.EventMetadataMapperImpl;
import com.backend.adapter.in.mapper.LocationMapperImpl;
import com.backend.domain.happening.metadata.EventMetadata;
import com.backend.adapter.in.dto.shared.EventMetadataDto;
import com.backend.adapter.in.mapper.EventMetadataMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.backend.in.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        EventMetadataMapperImpl.class,
        LocationMapperImpl.class
})
public class EventMetadataMapperTest {

    @Autowired
    private EventMetadataMapper mapper;

    @Test
    void testToDomain() {
        EventMetadata result = mapper.toDomain(eventMetadataDto);

        assertEquals(eventMetadataDto.authorUsername(), result.authorUsername());
        assertEquals(eventMetadataDto.startTime(), result.startTime());
        assertEquals(eventMetadataDto.endTime(), result.endTime());

        assertEquals(eventMetadataDto.location().latitude(), result.location().latitude());
        assertEquals(eventMetadataDto.location().longitude(), result.location().longitude());
        assertEquals(eventMetadataDto.location().address(), result.location().address());
    }

    @Test
    void testToDto() {
        EventMetadataDto result = mapper.toDto(eventMetadata);

        assertEquals(eventMetadata.authorUsername(), result.authorUsername());
        assertEquals(eventMetadata.startTime(), result.startTime());
        assertEquals(eventMetadata.endTime(), result.endTime());

        assertEquals(eventMetadata.location().latitude(), result.location().latitude());
        assertEquals(eventMetadata.location().longitude(), result.location().longitude());
        assertEquals(eventMetadata.location().address(), result.location().address());
    }

    @Test
    void testMetadataToEventMetadataDto_valid() {
        EventMetadataDto result = mapper.metadataToEventMetadataDto(eventMetadata);
        assertEquals(eventMetadata.authorUsername(), result.authorUsername());
    }

}
