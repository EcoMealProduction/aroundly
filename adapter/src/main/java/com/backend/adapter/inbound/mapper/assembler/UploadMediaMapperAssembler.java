package com.backend.adapter.inbound.mapper.assembler;

import com.backend.port.inbound.commands.UploadMediaCommand;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Maps {@link MultipartFile} uploads to {@link UploadMediaCommand}.
 */
@Component
public class UploadMediaMapperAssembler {

  /**
   * Converts multiple files to upload commands.
   *
   * @param files uploaded files
   * @return upload commands
   */
  public Set<UploadMediaCommand> toUploads(Set<MultipartFile> files) {
    if (files == null || files.isEmpty()) return Set.of();

    return files.stream()
      .map(this::toUpload)
      .collect(Collectors.toSet());
  }

  /**
   * Converts a single file to an upload command.
   *
   * @param file uploaded file
   * @return upload command
   */
  public UploadMediaCommand toUpload(MultipartFile file) {
    try {
      return new UploadMediaCommand(
          file.getInputStream(),
          sanitizeFilename(file.getOriginalFilename()),
          file.getSize(),
          safeContentType(file.getContentType()));
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to read upload: " + file.getOriginalFilename(), e);
    }
  }

  private static String sanitizeFilename(String name) {
    if (name == null || name.isBlank()) return "file";
    return name.replaceAll("[^a-zA-Z0-9._-]", "_");
  }

  private static String safeContentType(String ct) {
    return (ct == null || ct.isBlank()) ? "application/octet-stream" : ct;
  }
}
