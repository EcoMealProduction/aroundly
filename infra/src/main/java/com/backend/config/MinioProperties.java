package com.backend.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Holds configuration properties required to connect to a MinIO object storage service.
 *
 * This class centralizes all externalized MinIO settings and makes them available
 * as strongly typed fields. By binding values from application.properties, i
 * t allows the application to:
 *
 *   1. Establish a connection to the correct MinIO server instance via {@link #endpoint}.
 *   2. Authenticate securely using {@link #accessKey} and {@link #secretKey}.
 *   3. Interact with the correct storage location through the configured {@link #bucket}.
 *
 * Validation annotations (e.g. {@link NotBlank}) ensure the application fails fast
 * at startup if any required property is missing. This prevents runtime errors when
 * performing file uploads or downloads.
 *
 * Typical usage: inject this class wherever MinIO clients or services are configured,
 * so connection details are managed in one place and not hardcoded in the codebase.
 */
@Data
@Validated
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
  @NotBlank private String endpoint;
  @NotBlank private String accessKey;
  @NotBlank private String secretKey;
  @NotBlank private String bucket;
}
