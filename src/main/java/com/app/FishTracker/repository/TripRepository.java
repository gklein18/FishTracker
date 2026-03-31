package com.app.FishTracker.repository;

import com.app.FishTracker.dto.trip.TripSummaryDTO;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
        SELECT new com.app.FishTracker.dto.trip.TripSummaryDTO(
            t.id,
            t.location,
            t.tripDate,
            t.durationHours,
            COUNT(c.id)
        )
        FROM Trip t
        LEFT JOIN t.catches c
        GROUP BY t
        """)
    List<TripSummaryDTO> findAllTripSummaries();

    @Query("""
        SELECT new com.app.FishTracker.dto.trip.TripSummaryDTO(
            t.id,
            t.location,
            t.tripDate,
            t.durationHours,
            COUNT(c.id)
        )
        FROM Trip t
        LEFT JOIN t.catches c
        WHERE t.user.id = :userId
        GROUP BY t
        """)
    List<TripSummaryDTO> findUserTripSummaries(Long userId);

    List<Trip> findTop3ByUserIdOrderByIdDesc(Long userId);
}
