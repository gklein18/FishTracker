package com.app.FishTracker.controller;

import com.app.FishTracker.dto.trip.CreateTripRequest;
import com.app.FishTracker.dto.trip.TripDetailDTO;
import com.app.FishTracker.dto.trip.TripSummaryDTO;
import com.app.FishTracker.dto.trip.UpdateTripRequest;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<TripSummaryDTO> getTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripDetailDTO getTrip(@PathVariable Long id) {
        return tripService.getTrip(id);
    }

    @GetMapping("/user/{id}")
    public List<TripSummaryDTO> getUserTrips(@PathVariable Long id) {
        return tripService.findByUserId(id);
    }

    @PostMapping
    public TripDetailDTO createTrip(@RequestBody CreateTripRequest request) {
        return tripService.createTrip(request);
    }

    @PutMapping("/update")
    public TripDetailDTO updateTrip(@RequestBody UpdateTripRequest request) {
        return tripService.updateTrip(request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }

    @GetMapping("/recent/{id}")
    public List<TripSummaryDTO> getRecentTrips(@PathVariable Long id) {
        return tripService.getRecentTrips(id);
    }

    @GetMapping("/user/stats/{id}")
    public List<Integer> getUserStats(@PathVariable Long id) {
        return tripService.getUserStats(id);
    }
}
