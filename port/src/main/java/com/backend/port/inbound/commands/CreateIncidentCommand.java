package com.backend.port.inbound.commands;

import com.backend.domain.media.Media;
import java.util.Set;

/**
 * Command object used for creating a new Incident.
 *
 * @param title       the title of the incident
 * @param description the description of the incident
 * @param media       the set of media associated with the incident
 */
public record CreateIncidentCommand(String title, String description, Set<Media> media,
                                    double lat, double lon) {

}
