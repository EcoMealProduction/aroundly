package com.backend.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.HappeningId;
import com.backend.domain.happening.Incident;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.domain.media.MediaKind;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidentTest {

  private Incident incident;

  @BeforeEach
  void setup() throws URISyntaxException {
    incident = createIncident();
  }

  @Test
  public void testIncidentCreationTime() {
    int actualCreationTime = LocalDateTime.ofInstant(
        incident.createdAt(), ZoneId.systemDefault()).getMinute();
    int expectedCreationTime = LocalDateTime.now().getMinute();

    assert expectedCreationTime == actualCreationTime;
  }

  @Test
  public void testIncidentIsNotExpired() {
    assertFalse(incident.isExpired());
  }

  @Test
  public void testIncidentExpired() {
    Instant expiry = incident.expiresAt();
    Instant fakeNow = expiry.plus(Duration.ofHours(1));
    Clock fakeClock = Clock.fixed(fakeNow, ZoneOffset.UTC);

    assertTrue(incident.isExpired(fakeClock));
  }

  @Test
  public void testIncidentIsNotReadyToDelete() {
    assertFalse(incident.isDeleted());
  }

  private Incident createIncident() throws URISyntaxException {
    return new Incident(
        new HappeningId(1L),
        new ActorId(1L),
        new LocationId(1L),
        Set.of(new Media(MediaKind.IMAGE, "type", new URI("/path/"))),
        "Title",
        "description");
  }

}
