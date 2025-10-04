package com.backend.adapter.outbound.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

/**
 * Entity representing a stored media object.
 *
 * This entity contains metadata about uploaded media, including
 * its storage key, content type, size, and creation timestamp.
 */
@Entity(name = "medias")
public class MediaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_id_seq")
  @SequenceGenerator(name = "media_id_seq", sequenceName = "media_id_seq", allocationSize = 1)
  private long id;

  @Column(name = "key", nullable = false, unique = true)
  private String key;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "size", nullable = false)
  private long size;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumn(name = "happening_id", foreignKey = @ForeignKey(name = "FK_MEDIA_HAPPENING"))
  private HappeningEntity happeningEntity;

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

  public HappeningEntity getHappeningEntity() {
    return happeningEntity;
  }

  public void setHappeningEntity(HappeningEntity happeningEntity) {
    this.happeningEntity = happeningEntity;
  }
}
