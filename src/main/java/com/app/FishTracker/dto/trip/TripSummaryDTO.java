package com.app.FishTracker.dto.trip;

import java.time.LocalDate;

public class TripSummaryDTO {

    private Long id;
    private String location;
    private LocalDate tripDate;
    private Double durationHours;
    private Long catchCount;

    public TripSummaryDTO(Long id, String location, LocalDate tripDate, Double durationHours, Long catchCount) {
        this.id = id;
        this.location = location;
        this.tripDate = tripDate;
        this.durationHours = durationHours;
        this.catchCount = catchCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public Double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public Long getCatchCount() {
        return catchCount;
    }

    public void setCatchCount(Long catchCount) {
        this.catchCount = catchCount;
    }
}
