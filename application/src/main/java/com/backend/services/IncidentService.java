package com.backend.services;

import com.backend.domain.actor.ActorId;
import com.backend.domain.happening.Happening;
import com.backend.domain.happening.Incident;
import com.backend.domain.happening.old.OldIncident;
import com.backend.domain.location.Location;
import com.backend.domain.location.LocationId;
import com.backend.domain.media.Media;
import com.backend.port.inbound.ActorUseCase;
import com.backend.port.inbound.IncidentUseCase;
import com.backend.port.inbound.commands.CreateIncidentCommand;
import com.backend.port.inbound.commands.RadiusCommand;
import com.backend.port.outbound.IncidentRepository;
import com.backend.port.outbound.LocationRepository;
import com.backend.services.authentication.SecurityCurrentActorExtractor;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for handling operations on Incident entities.
 * Delegates persistence to the IncidentRepository.
 *
 * Registered as a Spring {@link Service} for business logic orchestration.
 */
@Service
public class IncidentService implements IncidentUseCase {

    private final IncidentRepository incidentRepository;
    private final LocationRepository locationRepository;
    private final ActorUseCase actorExtractor;

  public IncidentService(
      IncidentRepository incidentRepository,
      LocationRepository locationRepository,
      ActorUseCase actorExtractor) {

    this.incidentRepository = incidentRepository;
    this.locationRepository = locationRepository;
    this.actorExtractor = actorExtractor;
  }

  /**
     * Retrieves all Incident entries within a given visibility range.
     *
     * @param radiusCommand the command containing center coordinates and radius in meters
     *
     * @return list of matching {@code Incident} instances.
     */
    @Override
    public List<Incident> findAllInGivenRange(RadiusCommand radiusCommand) {
        final double userLatitude = radiusCommand.lat();
        final double userLongitude = radiusCommand.lon();
        final double radiusMeters = radiusCommand.radius();

        return incidentRepository.findAllInGivenRange(userLatitude, userLongitude, radiusMeters);
    }

  /**
   * Finds all incidents created by a specific actor.
   *
   * @param actorId identifier of the actor (user)
   * @return list of incidents reported by the given actor
   */
    @Override
    public List<Happening> findByActorId(String actorId) {
      return incidentRepository.findByUserId(actorId);
    }

    /**
     * Retrieves a specific Incident by its ID.
     *
     * @param id the unique identifier
     * @return the matching Incident
     * @throws IllegalArgumentException if not found
     */
    @Override
    public Happening findById(long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident not found"));
    }

    /**
     * Persists a new Incident.
     *
     * @param createIncidentCommand the command containing incident details
     * @return the saved instance.
     */
    @Override
    public Incident create(CreateIncidentCommand createIncidentCommand) {
        final ActorId actorId = actorExtractor.extractId();
        final String title = createIncidentCommand.title();
        final String description = createIncidentCommand.description();
        final Set<Media> media = createIncidentCommand.media();
        final Optional<Location> location = locationRepository
            .findByCoordinate(createIncidentCommand.lat(), createIncidentCommand.lon());
      final LocationId locationId = location.get().id();

        Incident incident = Incident.builder()
            .actorId(actorId)
            .locationId(locationId)
            .title(title)
            .description(description)
            .media(media)
            .build();

        return incidentRepository.save(incident);
    }

    /**
     * Updates an existing {@link OldIncident} by applying changes from a new instance.
     * Ensures the types of the original and new incident match.
     *
     * @param id ID of the happening to update.
     * @param createIncidentCommand the new values.
     * @return the updated and saved instance.
     * @throws IllegalArgumentException if types mismatch or ID not found.
     */
    @Override
    public Incident update(long id, CreateIncidentCommand createIncidentCommand) {
        final Incident existingIncident = (Incident) findById(id);
        final String updatedTitle = createIncidentCommand.title();
        final String updatedDescription = createIncidentCommand.description();
        final Set<Media> updatedMedia = createIncidentCommand.media();

        Incident updatedExistingOldIncident = existingIncident.toBuilder()
            .title(updatedTitle)
            .description(updatedDescription)
            .media(updatedMedia)
            .build();

        return incidentRepository.save(updatedExistingOldIncident);
    }

    /**
     * Extends the expiration time of an incident by confirming it.
     *
     * @param incidentId the ID of the incident.
     * @return a new {@link OldIncident} instance with extended lifespan.
     * @throws IllegalStateException if the given ID does not point to an {@code Incident}.
     */
    @Override
    public Incident confirm(long incidentId) {
      Incident incident = (Incident) findById(incidentId);
      incident.confirmIncident();

      return incidentRepository.save(incident);
    }

  /**
   * Finds the incident by ID, applies a denial, and saves it.
   * Denial increments the counters and may eventually mark
   * the incident as deleted.
   *
   * @param incidentId the incident identifier
   * @return the updated incident
   */
    @Override
    public Incident deny(long incidentId) {
      Incident incident = (Incident) findById(incidentId);
      incident.denyIncident();

      return incidentRepository.save(incident);
    }

  /**
   * Deletes the incident if it is expired or otherwise considered deleted.
   * If the incident is still active, nothing happens.
   *
   * @param incidentId the incident identifier
   */
    @Override
    public void deleteIfExpired(long incidentId) {
      Incident incident = (Incident) findById(incidentId);
      if (incident.isDeleted()) {
        incidentRepository.deleteById(incidentId);
      }
    }

  /**
     * Deletes a {@link OldIncident} by ID.
     *
     * @param incidentId the unique identifier of the entity to delete.
     */
    @Override
    public void deleteById(long incidentId) {
        incidentRepository.deleteById(incidentId);
    }
}
