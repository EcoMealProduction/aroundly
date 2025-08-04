package com.backend.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "incident")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("INCIDENT")
public class IncidentEntity extends HappeningEntity {

    @Column(name = "reported_time", nullable = false)
    private LocalDateTime reportedTime = LocalDateTime.now();

    @Column(name = "severity_range_km")
    private Integer severityRangeKm = 5;

    @Column(name = "confirms")
    private Integer confirms = 0;

    @Column(name = "denies")
    private Integer denies = 0;
}
