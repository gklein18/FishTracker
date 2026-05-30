package com.app.FishTracker.service;

import com.app.FishTracker.dto.catchrecord.CatchRecordDTO;
import com.app.FishTracker.dto.trip.CreateTripRequest;
import com.app.FishTracker.dto.trip.TripDetailDTO;
import com.app.FishTracker.dto.trip.TripSummaryDTO;
import com.app.FishTracker.dto.trip.UpdateTripRequest;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.model.User;
import com.app.FishTracker.repository.CatchRecordRepository;
import com.app.FishTracker.repository.TripRepository;
import com.app.FishTracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @InjectMocks
    private TripService tripService;

    @Mock
    private CatchRecordRepository catchRecordRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    User user;
    Trip trip;
    TripDetailDTO tripDetailDTO;
    List<Trip> trips;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@email.com");
        user.setPasswordHash("12345678");
        user.setId(Long.parseLong("1"));

        trip = new Trip();
        trip.setLocation("lake");
        trip.setTripDate(LocalDate.of(2026, 10, 31));
        trip.setDurationHours(2.0);
        trip.setUser(user);
        trip.setId(Long.parseLong("1"));
        trip.setCatches(new ArrayList<>());

        tripDetailDTO = new TripDetailDTO();
        tripDetailDTO.setId(Long.parseLong("1"));
        tripDetailDTO.setLocation("lake");
        tripDetailDTO.setTripDate(trip.getTripDate());
        tripDetailDTO.setCatches(new ArrayList<>());

        trips = new ArrayList<>();
        trips.add(trip);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getUserStatsTest() {
        int trips = 1, catches = 3, pbs = 2;

        when(tripRepository.countByUserId(anyLong())).thenReturn(1);
        when(catchRecordRepository.countByUserId(anyLong())).thenReturn(3);
        when(catchRecordRepository.countPBs(anyLong())).thenReturn(2);

        List<Integer> results = tripService.getUserStats(Long.parseLong("1"));

        assertEquals(trips, results.getFirst());
        assertEquals(catches, results.get(1));
        assertEquals(pbs, results.getLast());
    }

    @Test
    void createTripTest() {
        CreateTripRequest create = new CreateTripRequest();
        create.setLocation("lake");
        create.setTripDate(LocalDate.of(2026, 10, 31));
        create.setUserId(user.getId());

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(tripRepository.save(any())).thenReturn(trip);

        TripDetailDTO result = tripService.createTrip(create);

        assertEquals(tripDetailDTO.getTripDate(), result.getTripDate());
        assertEquals(tripDetailDTO.getId(), result.getId());
        assertEquals(tripDetailDTO.getLocation(), result.getLocation());
        assertEquals(tripDetailDTO.getCatches().size(), result.getCatches().size());
    }

    @Test
    void updateTripTest() {
        UpdateTripRequest update = new UpdateTripRequest();
        update.setTripId(Long.parseLong("1"));
        update.setDurationHours(2.0);

        when(tripRepository.findById(anyLong())).thenReturn(Optional.ofNullable(trip));
        when(tripRepository.save(any())).thenReturn(trip);

        tripService.updateTrip(update);

        assertEquals(update.getDurationHours(), trip.getDurationHours());
    }

    @Test
    void getRecentCatchesTest() {
        trips.add(trip);
        trips.add(trip);

        when(tripRepository.findTop3ByUserIdOrderByIdDesc(anyLong())).thenReturn(trips);

        List<TripSummaryDTO> result = tripService.getRecentTrips(Long.parseLong("1"));

        assertEquals(3, result.size());
    }
}
