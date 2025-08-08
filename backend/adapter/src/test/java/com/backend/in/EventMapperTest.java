package com.backend.in;

import com.backend.adapter.in.mapper.EventMapperImpl;
import com.backend.adapter.in.mapper.EventMetadataMapperImpl;
import com.backend.adapter.in.mapper.LocationMapperImpl;
import com.backend.domain.happening.Event;
import com.backend.adapter.in.dto.request.EventRequestDto;
import com.backend.adapter.in.dto.response.EventResponseDto;
import com.backend.adapter.in.mapper.EventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.backend.in.MapperFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        EventMapperImpl.class,
        EventMetadataMapperImpl.class,
        LocationMapperImpl.class
})
public class EventMapperTest {

    @Autowired
    private EventMapper eventMapper;

    @Test
    void testEventRequestDtoToEvent() {
        EventRequestDto dto = eventRequestDto;
        Event event = eventMapper.toEvent(dto);

        assertEquals(dto.title(), event.title());
        assertEquals(dto.description(), event.description());

        assertNotNull(event.metadata());
        assertEquals(dto.eventMetadata().authorUsername(), event.metadata().authorUsername());
        assertEquals(dto.eventMetadata().startTime(), event.metadata().startTime());
        assertEquals(dto.eventMetadata().endTime(), event.metadata().endTime());

        assertEquals(dto.eventMetadata().location().latitude(), event.metadata().location().latitude());
        assertEquals(dto.eventMetadata().location().longitude(), event.metadata().location().longitude());
        assertEquals(dto.eventMetadata().location().address(), event.metadata().location().address());

        assertNull(event.sentimentEngagement());
        assertTrue(event.comments().isEmpty());
    }

    @Test
    void testEventToEventResponseDto() {
        Event event = domainEvent;
        EventResponseDto dto = eventMapper.toResponseDto(event);

        assertEquals(event.title(), dto.title());
        assertEquals(event.description(), dto.description());

        assertNotNull(dto.eventMetadata());
        assertEquals(event.metadata().authorUsername(), dto.eventMetadata().authorUsername());
        assertEquals(event.metadata().startTime(), dto.eventMetadata().startTime());
        assertEquals(event.metadata().endTime(), dto.eventMetadata().endTime());

        assertEquals(event.metadata().location().latitude(), dto.eventMetadata().location().latitude());
        assertEquals(event.metadata().location().longitude(), dto.eventMetadata().location().longitude());
        assertEquals(event.metadata().location().address(), dto.eventMetadata().location().address());

        assertNull(dto.sentiment());
        assertNull(dto.comments());
    }
}
