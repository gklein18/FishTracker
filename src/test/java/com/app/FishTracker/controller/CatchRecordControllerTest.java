package com.app.FishTracker.controller;

import com.app.FishTracker.dto.catchrecord.CreateCatchRequest;
import com.app.FishTracker.model.CatchRecord;
import com.app.FishTracker.model.Fish;
import com.app.FishTracker.model.Trip;
import com.app.FishTracker.model.User;
import com.app.FishTracker.repository.CatchRecordRepository;
import com.app.FishTracker.repository.FishRepository;
import com.app.FishTracker.repository.TripRepository;
import com.app.FishTracker.repository.UserRepository;
import com.app.FishTracker.service.CatchRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CatchRecordControllerTest {

    @Mock
    private CatchRecordService catchRecordService;

    @Mock
    private CatchRecordRepository catchRecordRepository;

    @Mock
    private FishRepository fishRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CatchRecordController catchRecordController;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    private MockMvc mockMvc;

    Trip trip;
    User user;
    Fish fish;
    CatchRecord cr;
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

        catches = new ArrayList<>();
        catches.add(cr);

        mockMvc = MockMvcBuilders.standaloneSetup(catchRecordController).build();
    }

    @AfterEach
    void tearDown() {}

    @Test
    void createCatchRecordTest() throws Exception {
        CreateCatchRequest create = new CreateCatchRequest();
        create.setTripId(trip.getId());
        create.setFishId(Long.parseLong("5"));
        create.setLength(1.0);
        create.setWeight(2.0);
        create.setUserId(user.getId());
        create.setDateCaught(LocalDateTime.of(2026,10, 31, 8, 30, 30));

        mockMvc.perform(post("/api/catch/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().isOk());
    }

    @Test
    void createCatchRecordTestInvalid() throws Exception {
        CreateCatchRequest create = new CreateCatchRequest();
        create.setTripId(trip.getId());
        create.setFishId(Long.parseLong("5"));
        create.setLength(1000.0);
        create.setWeight(2000.0);
        create.setUserId(user.getId());
        create.setDateCaught(LocalDateTime.of(2026,10, 31, 8, 30, 30));

        mockMvc.perform(post("/api/catch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserCatchesTest() throws Exception {
        mockMvc.perform(get("/api/catch/user/1"))
                .andExpect(status().isOk());
    }
}
