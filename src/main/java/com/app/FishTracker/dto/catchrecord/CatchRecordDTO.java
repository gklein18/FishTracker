package com.app.FishTracker.dto.catchrecord;

import java.time.LocalDateTime;

public class CatchRecordDTO {

    private Long id;
    private String fishName;
    private Double length;
    private Double weight;
    private LocalDateTime dateCaught;
    private boolean personalBest;
    private Long tripId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(boolean personalBest) {
        this.personalBest = personalBest;
    }

    public LocalDateTime getDateCaught() {
        return dateCaught;
    }

    public void setDateCaught(LocalDateTime dateCaught) {
        this.dateCaught = dateCaught;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
