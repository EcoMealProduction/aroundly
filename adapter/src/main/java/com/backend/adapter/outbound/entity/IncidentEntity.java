package com.backend.adapter.outbound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity(name = "incidents")
@Builder
public class IncidentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_id_seq")
    @SequenceGenerator(name = "incident_id_seq", sequenceName = "incident_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "happening_id", foreignKey = @ForeignKey(name = "FK_INCIDENT_HAPPENING"))
    private HappeningEntity happening;

    private LocalDateTime timePosted;
    private double range;
    private int confirms;
    private int denies;

    public IncidentEntity(
        long id,
        HappeningEntity happening,
        LocalDateTime timePosted,
        double range,
        int confirms,
        int denies) {

        this.id = id;
        this.happening = happening;
        this.timePosted = timePosted;
        this.range = range;
        this.confirms = confirms;
        this.denies = denies;
    }

    public IncidentEntity() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HappeningEntity getHappening() {
        return happening;
    }

    public void setHappening(HappeningEntity happening) {
        this.happening = happening;
    }

    public LocalDateTime getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(LocalDateTime timePosted) {
        this.timePosted = timePosted;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getConfirms() {
        return confirms;
    }

    public void setConfirms(int confirms) {
        this.confirms = confirms;
    }

    public int getDenies() {
        return denies;
    }

    public void setDenies(int denies) {
        this.denies = denies;
    }
}