package com.app.FishTracker.dto.catchrecord;

public class UpdateCatchRequest {

    private Long fishId;
    private Double length;
    private Double weight;
    private Long catchId;


    public Long getFishId() {
        return fishId;
    }

    public void setFishId(Long fishId) {
        this.fishId = fishId;
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

    public Long getCatchId() {
        return catchId;
    }

    public void setCatchId(Long catchId) {
        this.catchId = catchId;
    }
}
