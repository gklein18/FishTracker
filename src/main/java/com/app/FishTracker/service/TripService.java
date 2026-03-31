package com.app.FishTracker.service;

import com.app.FishTracker.dto.catchrecord.CatchRecordDTO;
import com.app.FishTracker.dto.trip.CreateTripRequest;
import com.app.FishTracker.dto.trip.TripDetailDTO;
import com.app.FishTracker.dto.trip.TripSummaryDTO;
import com.app.FishTracker.dto.trip.UpdateTripRequest;
import com.app.FishTracker.model.CatchRecord;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.model.User;
import com.app.FishTracker.repository.TripRepository;
import com.app.FishTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final CatchRecordService catchRecordService;
    private final UserRepository userRepository;

    @Autowired
    public TripService(TripRepository tripRepository, CatchRecordService catchRecordService, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.catchRecordService = catchRecordService;
        this.userRepository = userRepository;
    }

    public List<TripSummaryDTO> findByUserId(Long userId) {
        return tripRepository.findUserTripSummaries(userId);
    }

    public TripDetailDTO createTrip(CreateTripRequest request) {
        Trip trip = new Trip();
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        List<CatchRecord> catches = new ArrayList<>();

        trip.setLocation(request.getLocation());
        trip.setTripDate(request.getTripDate());
        trip.setUser(user);
        trip.setCatches(catches);
        trip.setDurationHours(0.0);

        return toDetailDTO(tripRepository.save(trip));
    }

    public TripDetailDTO updateTrip(UpdateTripRequest request) {
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Trip not found"));

        trip.setDurationHours(request.getDurationHours());

        return toDetailDTO(tripRepository.save(trip));
    }

    public void deleteTrip(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));

        tripRepository.delete(trip);
    }

    public List<TripSummaryDTO> getAllTrips() {
        return tripRepository.findAllTripSummaries();
    }

    public TripDetailDTO getTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
        return toDetailDTO(trip);
    }

    public List<TripSummaryDTO> getRecentTrips(Long userId) {
        List<Trip> recents = tripRepository.findTop3ByUserIdOrderByIdDesc(userId);
        List<TripSummaryDTO> recentsDTO = new ArrayList<>();

        for (Trip trip : recents) {
            recentsDTO.add(new TripSummaryDTO(trip.getId(),
                                    trip.getLocation(),
                                    trip.getTripDate(),
                                    trip.getDurationHours(),
                                    (long) trip.getCatches().size()));
        }

        return recentsDTO;
    }

    public TripDetailDTO toDetailDTO(Trip trip) {
        TripDetailDTO dto = new TripDetailDTO();

        dto.setId(trip.getId());
        dto.setLocation(trip.getLocation());
        dto.setTripDate(trip.getTripDate());

        List<CatchRecordDTO> dtoList = new ArrayList<>();
        List<CatchRecord> catchList = trip.getCatches();

        for (CatchRecord record : catchList) {
            dtoList.add(catchRecordService.toDTO(record));
        }
        dto.setCatches(dtoList);

        return dto;
    }
}
