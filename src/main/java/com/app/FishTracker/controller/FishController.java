package com.app.FishTracker.controller;

import com.app.FishTracker.dto.fish.FishDTO;
import com.app.FishTracker.service.FishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fish")
public class FishController {
    private final FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping("/all")
    public List<FishDTO> getAllFish() {
        return fishService.findAll();
    }
}
