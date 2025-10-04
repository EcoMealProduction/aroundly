package com.backend.adapter.outbound.entity;

import static jakarta.persistence.CascadeType.ALL;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "happenings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HappeningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "happening_id_seq")
    @SequenceGenerator(name = "happening_id_seq", sequenceName = "happening_id_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(name = "FK_HAPPENING_CLIENT"))
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "FK_HAPPENING_LOCATION"))
    private LocationEntity location;

    @OneToMany(mappedBy = "happeningEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MediaEntity> media = new HashSet<>();

    private LocalDateTime createdAt;

    public void addMedia(MediaEntity m) {
        media.add(m);
        m.setHappeningEntity(this);
    }
    public void removeMedia(MediaEntity m) {
        media.remove(m);
        m.setHappeningEntity(null);
    }
}