package com.backend.services.happening;

import com.backend.happening.Event;
import org.springframework.stereotype.Component;

@Component
public class EventStrategy implements HappeningStrategy<Event> {

    @Override
    public Event update(Event existing, Event updated) {
        return existing.toBuilder()
                .title(updated.title())
                .description(updated.description())
                .comments(updated.comments())
                .sentimentEngagement(updated.sentimentEngagement())
                .metadata(updated.metadata())
                .build();
    }

    @Override
    public Class<Event> getSupportedType() {
        return Event.class;
    }
}
