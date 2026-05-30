package com.app.FishTracker.service;

import com.app.FishTracker.dto.catchrecord.CatchRecordDTO;
import com.app.FishTracker.dto.catchrecord.CreateCatchRequest;
import com.app.FishTracker.dto.catchrecord.UpdateCatchRequest;
import com.app.FishTracker.model.CatchRecord;
import com.app.FishTracker.model.Fish;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.model.User;
import com.app.FishTracker.repository.CatchRecordRepository;
import com.app.FishTracker.repository.FishRepository;
import com.app.FishTracker.repository.TripRepository;
import com.app.FishTracker.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@ExtendWith(MockitoExtension.class)
public class CatchRecordServiceTest {

    @InjectMocks
    private CatchRecordService catchRecordService;

    @Mock
    private CatchRecordRepository catchRecordRepository;

    @Mock
    private FishRepository fishRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    CatchRecord cr;
    Trip trip;
    User user;
    Fish fish;
    CatchRecordDTO crDTO;
    List<CatchRecord> catches;

    @BeforeEach
    void setUp() {
        fish = new Fish();
        fish.setName("Bluegill");
        fish.setScientificName("test");
        fish.setId(Long.parseLong("5"));

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

        cr = new CatchRecord();
        cr.setPersonalBest(true);
        cr.setTrip(trip);
        cr.setId(Long.parseLong("1"));
        cr.setLength(1.0);
        cr.setWeight(2.0);
        cr.setUser(user);
        cr.setDateCaught(LocalDateTime.of(2026,10, 31, 8, 30, 30));
        cr.setFish(fish);

        crDTO = new CatchRecordDTO();
        crDTO.setPersonalBest(true);
        crDTO.setTripId(trip.getId());
        crDTO.setId(Long.parseLong("1"));
        crDTO.setLength(1.0);
        crDTO.setWeight(2.0);
        crDTO.setDateCaught(LocalDateTime.of(2026,10, 31, 8, 30, 30));
        crDTO.setFishName(fish.getName());

        catches = new ArrayList<>();
        catches.add(cr);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findByTripIdTest() {
        when(catchRecordRepository.findByTripId(anyLong())).thenReturn(catches);

        List<CatchRecordDTO> ret = catchRecordService.findByTripId(Long.parseLong("1"));
        CatchRecordDTO result = ret.getFirst();

        assertEquals(crDTO.getId(), result.getId());
        assertEquals(crDTO.getFishName(), result.getFishName());
        assertEquals(crDTO.getTripId(), result.getTripId());
        assertEquals(crDTO.getLength(), result.getLength());
        assertEquals(crDTO.getWeight(), result.getWeight());
        assertEquals(crDTO.isPersonalBest(), result.isPersonalBest());
    }

    @Test
    void findByUserTest() {
        when(catchRecordRepository.findByUserId(anyLong())).thenReturn(catches);

        List<CatchRecordDTO> ret = catchRecordService.findByUser(Long.parseLong("1"));
        CatchRecordDTO result = ret.getFirst();

        assertEquals(crDTO.getId(), result.getId());
        assertEquals(crDTO.getFishName(), result.getFishName());
        assertEquals(crDTO.getTripId(), result.getTripId());
        assertEquals(crDTO.getLength(), result.getLength());
        assertEquals(crDTO.getWeight(), result.getWeight());
        assertEquals(crDTO.isPersonalBest(), result.isPersonalBest());
    }

    @Test
    void getAllCatchesTest() {
        when(catchRecordRepository.findAll()).thenReturn(catches);

        List<CatchRecordDTO> ret = catchRecordService.getAllCatches();
        CatchRecordDTO result = ret.getFirst();

        assertEquals(crDTO.getId(), result.getId());
        assertEquals(crDTO.getFishName(), result.getFishName());
        assertEquals(crDTO.getTripId(), result.getTripId());
        assertEquals(crDTO.getLength(), result.getLength());
        assertEquals(crDTO.getWeight(), result.getWeight());
        assertEquals(crDTO.isPersonalBest(), result.isPersonalBest());
    }

    @Test
    void createCatchRecordTest() {
        CreateCatchRequest create = new CreateCatchRequest();
        create.setTripId(trip.getId());
        create.setFishId(Long.parseLong("5"));
        create.setLength(1.0);
        create.setWeight(2.0);
        create.setUserId(user.getId());
        create.setDateCaught(LocalDateTime.of(2026,10, 31, 8, 30, 30));

        when(fishRepository.getReferenceById(anyLong())).thenReturn(fish);
        when(tripRepository.findById(anyLong())).thenReturn(Optional.ofNullable(trip));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(catchRecordRepository.save(any())).thenReturn(cr);

        CatchRecordDTO result = catchRecordService.createCatchRecord(create);

        assertEquals(crDTO.getId(), result.getId());
        assertEquals(crDTO.getFishName(), result.getFishName());
        assertEquals(crDTO.getTripId(), result.getTripId());
        assertEquals(crDTO.getLength(), result.getLength());
        assertEquals(crDTO.getWeight(), result.getWeight());
        assertEquals(crDTO.isPersonalBest(), result.isPersonalBest());
    }

    @Test
    void updateCatchRecordTest() {
        UpdateCatchRequest update = new UpdateCatchRequest();
        update.setLength(4.0);
        update.setWeight(5.0);
        update.setFishId(Long.parseLong("5"));
        update.setCatchId(Long.parseLong("1"));

        crDTO.setLength(4.0);
        crDTO.setWeight(5.0);


        when(catchRecordRepository.findByCatchId(anyLong())).thenReturn(cr);
        when(fishRepository.getReferenceById(anyLong())).thenReturn(fish);
        when(tripRepository.findById(anyLong())).thenReturn(Optional.ofNullable(trip));
        when(catchRecordRepository.save(any())).thenReturn(cr);

        CatchRecordDTO result = catchRecordService.updateCatchRecord(update, Long.parseLong("1"));

        assertEquals(crDTO.getId(), result.getId());
        assertEquals(crDTO.getFishName(), result.getFishName());
        assertEquals(crDTO.getTripId(), result.getTripId());
        assertEquals(crDTO.getLength(), result.getLength());
        assertEquals(crDTO.getWeight(), result.getWeight());
        assertEquals(crDTO.isPersonalBest(), result.isPersonalBest());
    }

    @Test
    void getRecentCatchesTest() {
        catches.add(cr);
        catches.add(cr);

        when(catchRecordRepository.findTop3ByUserIdOrderByIdDesc(anyLong())).thenReturn(catches);

        List<CatchRecordDTO> res = catchRecordService.getRecentCatches(Long.parseLong("1"));

        assertEquals(3, res.size());
    }

    @Test
    void personalBestTest() {
        cr.setLength(4.0);
        catches.add(cr);
        cr.setLength(5.0);
        catches.add(cr);

        when(catchRecordRepository.findByUserIdAndFishId(anyLong(), anyLong())).thenReturn(catches);
        doNothing().when(catchRecordRepository).setPersonalBestFalse(anyLong());

        assertTrue(catchRecordService.personalBest(Long.parseLong("1"), Long.parseLong("5"), 7.0));
        Assertions.assertFalse(catchRecordService.personalBest(Long.parseLong("1"), Long.parseLong("5"), 2.0));
    }
}
