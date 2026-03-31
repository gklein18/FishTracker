package com.app.FishTracker.dto.trip;

public class UpdateTripRequest {

    //add notes section later
    private Double durationHours;
    private Long tripId;

    public Double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }
}
