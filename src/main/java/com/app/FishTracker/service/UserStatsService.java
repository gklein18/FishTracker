package com.app.FishTracker.service;

import com.app.FishTracker.dto.stats.SpeciesCatchDTO;
import com.app.FishTracker.dto.stats.UserStatsDTO;
import com.app.FishTracker.repository.StatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserStatsService {
    
    private final StatsRepository statsRepository;
    
    public UserStatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }
    
    /**
     * Retrieve comprehensive statistics for a specific user
     * 
     * Optimized to use database-level aggregations to avoid N+1 queries.
     * All calculations are performed at the database level.
     * 
     * @param userId The ID of the user
     * @return UserStatsDTO containing all aggregated statistics
     */
    public UserStatsDTO getUserStats(Long userId) {
        // Fetch all stats using optimized repository queries
        Integer totalCatches = statsRepository.getTotalCatchCount(userId);
        Integer totalTrips = statsRepository.getTotalTripCount(userId);
        List<SpeciesCatchDTO> speciesCounts = statsRepository.getCatchesBySpecies(userId);
        Double heaviestWeight = statsRepository.getHeaviestFishWeight(userId);
        Double longestLength = statsRepository.getLongestFishLength(userId);
        
        // Fetch trip with most catches
        Long tripWithMostCatches = null;
        Integer maxCatchesInTrip = null;
        Object[] tripData = statsRepository.getTripWithMostCatches(userId);
        if (tripData != null && tripData.length > 0) {
            tripWithMostCatches = ((Number) tripData[0]).longValue();
            maxCatchesInTrip = ((Number) tripData[1]).intValue();
        }
        
        // Calculate average catches per trip
        Double averageCatchesPerTrip = null;
        if (totalTrips != null && totalTrips > 0 && totalCatches != null) {
            averageCatchesPerTrip = (double) totalCatches / totalTrips;
        }
        
        return new UserStatsDTO(
            userId,
            totalCatches != null ? totalCatches : 0,
            totalTrips != null ? totalTrips : 0,
            tripWithMostCatches,
            maxCatchesInTrip,
            heaviestWeight,
            longestLength,
            averageCatchesPerTrip,
            speciesCounts
        );
    }
}
