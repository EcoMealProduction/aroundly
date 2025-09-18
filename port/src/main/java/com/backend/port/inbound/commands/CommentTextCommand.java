package com.backend.port.inbound.commands;

/**
 * Command object used when creating or updating a comment.
 *
 * @param text the content of the comment
 */
public record CommentTextCommand(String text) { }
