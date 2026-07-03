package com.app.FishTracker.dto.stats;

public class SpeciesCatchDTO {
    private String speciesName;
    private Integer catchCount;

    public SpeciesCatchDTO() {}

    public SpeciesCatchDTO(String speciesName, Integer catchCount) {
        this.speciesName = speciesName;
        this.catchCount = catchCount;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public Integer getCatchCount() {
        return catchCount;
    }

    public void setCatchCount(Integer catchCount) {
        this.catchCount = catchCount;
    }
}
