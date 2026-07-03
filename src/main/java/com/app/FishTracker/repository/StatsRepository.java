package com.app.FishTracker.repository;

import com.app.FishTracker.dto.stats.SpeciesCatchDTO;
import com.app.FishTracker.model.CatchRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatsRepository {
    
    /**
     * Get total catch count for a user
     */
    @Query("""
        SELECT COUNT(c) FROM CatchRecord c
        WHERE c.user.id = :userId
        """)
    Integer getTotalCatchCount(@Param("userId") Long userId);
    
    /**
     * Get total trip count for a user
     */
    @Query("""
        SELECT COUNT(DISTINCT t.id) FROM Trip t
        WHERE t.user.id = :userId
        """)
    Integer getTotalTripCount(@Param("userId") Long userId);
    
    /**
     * Get catch counts grouped by species (only species with count > 0)
     */
    @Query("""
        SELECT new com.app.FishTracker.dto.stats.SpeciesCatchDTO(
            f.name,
            COUNT(c.id)
        )
        FROM CatchRecord c
        JOIN c.fish f
        WHERE c.user.id = :userId
        GROUP BY f.id, f.name
        HAVING COUNT(c.id) > 0
        ORDER BY COUNT(c.id) DESC
        """)
    List<SpeciesCatchDTO> getCatchesBySpecies(@Param("userId") Long userId);
    
    /**
     * Get the heaviest fish caught by a user
     */
    @Query("""
        SELECT MAX(c.weight) FROM CatchRecord c
        WHERE c.user.id = :userId
        """)
    Double getHeaviestFishWeight(@Param("userId") Long userId);
    
    /**
     * Get the longest fish caught by a user
     */
    @Query("""
        SELECT MAX(c.length) FROM CatchRecord c
        WHERE c.user.id = :userId
        """)
    Double getLongestFishLength(@Param("userId") Long userId);
    
    /**
     * Get the trip with the highest catch count for a user
     * Returns array: [tripId, catchCount]
     */
    @Query("""
        SELECT t.id, COUNT(c.id) as catch_count
        FROM Trip t
        LEFT JOIN t.catches c
        WHERE t.user.id = :userId
        GROUP BY t.id
        ORDER BY catch_count DESC
        LIMIT 1
        """)
    Object[] getTripWithMostCatches(@Param("userId") Long userId);
}
