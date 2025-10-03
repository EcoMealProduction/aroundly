package com.backend.adapter.outbound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    @SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "happening_id", foreignKey = @ForeignKey(name = "FK_EVENT_HAPPENING"))
    private HappeningEntity happening;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}