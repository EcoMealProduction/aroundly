package com.backend.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sentiment_engagement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentEngagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "happening_id", nullable = false, unique = true)
    private HappeningEntity happening;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "dislikes")
    private Integer dislikes = 0;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
