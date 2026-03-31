package com.app.FishTracker.service;

import com.app.FishTracker.dto.fish.FishDTO;
import com.app.FishTracker.model.Fish;
import com.app.FishTracker.repository.FishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FishService {

    private final FishRepository fishRepository;

    @Autowired
    public FishService(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    public FishDTO findByName(String name) {
        return toDTO(fishRepository.findByName(name));
    }

    public List<FishDTO> findAll() {
        List<Fish> allFish = fishRepository.findAll();
        List<FishDTO> fishDTOS = new ArrayList<>();

        for (Fish fish : allFish) {
            fishDTOS.add(toDTO(fish));
        }
        return fishDTOS;
    }


    public FishDTO toDTO(Fish fish) {
        FishDTO fishDTO = new FishDTO();

        fishDTO.setId(fish.getId());
        fishDTO.setName(fish.getName());
        fishDTO.setScientificName(fish.getScientificName());

        return fishDTO;
    }
}
