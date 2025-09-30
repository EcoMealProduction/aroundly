package com.backend.adapter.outbound.entity;

import jakarta.persistence.*;
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

    private String imageUrl;
    private LocalDateTime createdAt;
}