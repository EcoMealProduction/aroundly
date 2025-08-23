package com.backend.port.inbound.commands;

/**
 * Command object used for applying reactions to a comment, post, or incident.
 *
 * @param likes    the number of likes to apply
 * @param dislikes the number of dislikes to apply
 */
public record ReactionSummary(int likes, int dislikes) { }
