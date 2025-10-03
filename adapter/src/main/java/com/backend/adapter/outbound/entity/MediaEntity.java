package com.backend.adapter.outbound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

/**
 * Entity representing a stored media object.
 *
 * This entity contains metadata about uploaded media, including
 * its storage key, content type, size, and creation timestamp.
 */
@Entity
@Table(
    name = "media",
    indexes = { @Index(name = "ix_media_key", columnList = "key", unique = true) })
public class MediaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private long id;

  @Column(name = "key", nullable = false, unique = true, length = 1024)
  private String key;

  @Column(name = "content_type", nullable = false, length = 255)
  private String contentType;

  @Column(name = "size", nullable = false)
  private long size;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  public MediaEntity() { /* JPA */ }

  @PrePersist
  void onCreate() {
    if (createdAt == null) createdAt = OffsetDateTime.now();
  }

  public MediaEntity(
      long id,
      String key,
      String contentType,
      long size,
      OffsetDateTime createdAt) {

    this.id = id;
    this.key = key;
    this.contentType = contentType;
    this.size = size;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
