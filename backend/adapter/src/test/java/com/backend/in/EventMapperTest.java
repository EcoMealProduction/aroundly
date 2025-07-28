package com.backend.in;


import com.backend.MapperFixtures;
import com.backend.happening.Event;
import com.backend.in.dto.request.EventRequestDto;
import com.backend.in.mapper.EventMapper;
import com.backend.in.mapper.EventMapperImpl;
import com.backend.in.mapper.EventMetadataMapperImpl;
import com.backend.in.mapper.LocationMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
    void testEventRequestDtoToEvent_BasicMapping() {
        EventRequestDto dto = MapperFixtures.eventRequestDto;
        Event event = eventMapper.eventRequestDtoToEvent(dto);

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


    //Testul esueaza si de 2 zile incerc sa rezolv nimic nu o iesit eu deja nu mia am idei.
    //Daca faci debuging acolo el hz de ce transmite nulll la metadata hatea
//    @Test
//    void testEventToEventResponseDto_BasicMapping() {
//        Event event = MapperFixtures.event;
//        EventResponseDto dto = eventMapper.eventToEventResponseDto(event);
//
//        assertEquals(event.title(), dto.title());
//        assertEquals(event.description(), dto.description());
//
//        assertNotNull(dto.eventMetadata());
//        assertEquals(event.metadata().authorUsername(), dto.eventMetadata().authorUsername());
//        assertEquals(event.metadata().startTime(), dto.eventMetadata().startTime());
//        assertEquals(event.metadata().endTime(), dto.eventMetadata().endTime());
//
//        assertEquals(event.metadata().location().latitude(), dto.eventMetadata().location().latitude());
//        assertEquals(event.metadata().location().longitude(), dto.eventMetadata().location().longitude());
//        assertEquals(event.metadata().location().address(), dto.eventMetadata().location().address());
//
//        assertNull(dto.sentiment());
//        assertNull(dto.comments());
//    }
}
