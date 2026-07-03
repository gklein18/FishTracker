package com.app.FishTracker.dto.stats;

import java.util.List;

public class UserStatsDTO {
    private Long userId;
    private Integer totalCatches;
    private Integer totalTrips;
    private Long tripWithMostCatches;
    private Integer maxCatchesInTrip;
    private Double heaviestFishWeight;
    private Double longestFishLength;
    private Double averageCatchesPerTrip;
    private List<SpeciesCatchDTO> speciesCounts;

    public UserStatsDTO() {}

    public UserStatsDTO(Long userId, Integer totalCatches, Integer totalTrips,
                        Long tripWithMostCatches, Integer maxCatchesInTrip,
                        Double heaviestFishWeight, Double longestFishLength,
                        Double averageCatchesPerTrip, List<SpeciesCatchDTO> speciesCounts) {
        this.userId = userId;
        this.totalCatches = totalCatches;
        this.totalTrips = totalTrips;
        this.tripWithMostCatches = tripWithMostCatches;
        this.maxCatchesInTrip = maxCatchesInTrip;
        this.heaviestFishWeight = heaviestFishWeight;
        this.longestFishLength = longestFishLength;
        this.averageCatchesPerTrip = averageCatchesPerTrip;
        this.speciesCounts = speciesCounts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTotalCatches() {
        return totalCatches;
    }

    public void setTotalCatches(Integer totalCatches) {
        this.totalCatches = totalCatches;
    }

    public Integer getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(Integer totalTrips) {
        this.totalTrips = totalTrips;
    }

    public Long getTripWithMostCatches() {
        return tripWithMostCatches;
    }

    public void setTripWithMostCatches(Long tripWithMostCatches) {
        this.tripWithMostCatches = tripWithMostCatches;
    }

    public Integer getMaxCatchesInTrip() {
        return maxCatchesInTrip;
    }

    public void setMaxCatchesInTrip(Integer maxCatchesInTrip) {
        this.maxCatchesInTrip = maxCatchesInTrip;
    }

    public Double getHeaviestFishWeight() {
        return heaviestFishWeight;
    }

    public void setHeaviestFishWeight(Double heaviestFishWeight) {
        this.heaviestFishWeight = heaviestFishWeight;
    }

    public Double getLongestFishLength() {
        return longestFishLength;
    }

    public void setLongestFishLength(Double longestFishLength) {
        this.longestFishLength = longestFishLength;
    }

    public Double getAverageCatchesPerTrip() {
        return averageCatchesPerTrip;
    }

    public void setAverageCatchesPerTrip(Double averageCatchesPerTrip) {
        this.averageCatchesPerTrip = averageCatchesPerTrip;
    }

    public List<SpeciesCatchDTO> getSpeciesCounts() {
        return speciesCounts;
    }

    public void setSpeciesCounts(List<SpeciesCatchDTO> speciesCounts) {
        this.speciesCounts = speciesCounts;
    }
}
