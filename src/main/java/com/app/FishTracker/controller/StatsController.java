package com.app.FishTracker.controller;

import com.app.FishTracker.dto.stats.UserStatsDTO;
import com.app.FishTracker.service.UserStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController {
    
    private final UserStatsService userStatsService;
    
    public StatsController(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }
    
    /**
     * Retrieve comprehensive statistics for a specific user
     * 
     * @param userId The ID of the user
     * @return UserStatsDTO containing all aggregated statistics
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserStatsDTO> getUserStats(@PathVariable Long userId) {
        UserStatsDTO stats = userStatsService.getUserStats(userId);
        return ResponseEntity.ok(stats);
    }
}
