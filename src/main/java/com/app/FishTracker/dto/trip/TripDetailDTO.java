package com.app.FishTracker.dto.trip;

import com.app.FishTracker.dto.catchrecord.CatchRecordDTO;

import java.time.LocalDate;
import java.util.List;

public class TripDetailDTO {

    private Long id;
    private String location;
    private LocalDate tripDate;
    private List<CatchRecordDTO> catches;

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

    public List<CatchRecordDTO> getCatches() {
        return catches;
    }

    public void setCatches(List<CatchRecordDTO> catches) {
        this.catches = catches;
    }
}
