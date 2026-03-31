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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatchRecordService {

    private final CatchRecordRepository catchRecordRepository;
    private final FishRepository fishRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public CatchRecordService(CatchRecordRepository catchRecordRepository, FishRepository fishRepository, TripRepository tripRepository, UserRepository userRepository) {
        this.catchRecordRepository = catchRecordRepository;
        this.fishRepository = fishRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public List<CatchRecordDTO> findByTripId(Long tripId) {
        List<CatchRecord> catches = catchRecordRepository.findByTripId(tripId);
        List<CatchRecordDTO> catchesDTO = new ArrayList<>();

        for (CatchRecord record : catches) {
            catchesDTO.add(toDTO(record));
        }

        return catchesDTO;
    }

    public List<CatchRecordDTO> findByUser(Long userId) {
        List<CatchRecord> catches = catchRecordRepository.findByUserId(userId);
        List<CatchRecordDTO> catchesDTO = new ArrayList<>();

        for (CatchRecord record : catches) {
            catchesDTO.add(toDTO(record));
        }

        return catchesDTO;
    }

    public List<CatchRecordDTO> getAllCatches() {
        List<CatchRecord> catches =  catchRecordRepository.findAll();
        List<CatchRecordDTO> catchesDTO = new ArrayList<>();

        for (CatchRecord record : catches) {
            catchesDTO.add(toDTO(record));
        }

        return catchesDTO;
    }

    public CatchRecordDTO createCatchRecord(CreateCatchRequest request) {
        CatchRecord catchRecord = new CatchRecord();

        Fish fish = fishRepository.getReferenceById(request.getFishId());
        Trip trip = tripRepository.findById(request.getTripId()).orElseThrow(() -> new RuntimeException("Trip not found"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        catchRecord.setFish(fish);
        catchRecord.setTrip(trip);
        catchRecord.setLength(request.getLength());
        catchRecord.setWeight(request.getWeight());
        catchRecord.setDateCaught(request.getDateCaught());
        catchRecord.setLocation(request.getLocation());
        catchRecord.setUser(user);
        catchRecord.setPersonalBest(personalBest(user.getId(), fish.getId(), request.getLength()));

        CatchRecord saved = catchRecordRepository.save(catchRecord);

        return toDTO(saved);
    }

    public CatchRecordDTO updateCatchRecord(UpdateCatchRequest request, Long tripId) {
        CatchRecord catchRecord = catchRecordRepository.findByCatchId(request.getCatchId());

        Fish fish = fishRepository.getReferenceById(request.getFishId());
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));

        catchRecord.setFish(fish);
        catchRecord.setTrip(trip);
        catchRecord.setLength(request.getLength());
        catchRecord.setWeight(request.getWeight());
        catchRecord.setId(request.getCatchId());

        CatchRecord saved = catchRecordRepository.save(catchRecord);

        return toDTO(saved);
    }

    public void deleteCatchRecord(Long id) {
        CatchRecord catchRecord = catchRecordRepository.findByCatchId(id);
        catchRecordRepository.delete(catchRecord);
    }

    public CatchRecordDTO getCatchRecordById(Long id) {
        CatchRecord catchRecord = catchRecordRepository.findByCatchId(id);

        return toDTO(catchRecord);
    }

    public List<CatchRecordDTO> getRecentCatches(Long userId) {
        List<CatchRecord> catches =  catchRecordRepository.findTop3ByUserIdOrderByIdDesc(userId);
        List<CatchRecordDTO> catchesDTO = new ArrayList<>();

        for (CatchRecord record : catches) {
            catchesDTO.add(toDTO(record));
        }

        return catchesDTO;
    }

    public List<CatchRecordDTO> getRecentPersonalBests(Long id) {
        Pageable limit = PageRequest.of(0, 2);
        List<CatchRecord> catches =  catchRecordRepository.findRecentPBs(id, limit);
        List<CatchRecordDTO> catchesDTO = new ArrayList<>();

        for (CatchRecord record : catches) {
            catchesDTO.add(toDTO(record));
        }

        return catchesDTO;
    }


    public CatchRecordDTO toDTO(CatchRecord catchRecord) {
        CatchRecordDTO catchRecordDTO = new CatchRecordDTO();

        catchRecordDTO.setId(catchRecord.getId());
        catchRecordDTO.setLength(catchRecord.getLength());
        catchRecordDTO.setFishName(catchRecord.getFish().getName());
        catchRecordDTO.setWeight(catchRecord.getWeight());
        catchRecordDTO.setDateCaught(catchRecord.getDateCaught());
        catchRecordDTO.setPersonalBest(catchRecord.getPersonalBest());
        catchRecordDTO.setLocation(catchRecord.getLocation());

        return catchRecordDTO;
    }

    public boolean personalBest(Long userId, Long fishId, Double newCatch) {
        List<CatchRecord> catches =
                catchRecordRepository.findByUserIdAndFishId(userId, fishId);

        double maxLength = catches.stream()
                .mapToDouble(CatchRecord::getLength)
                .max()
                .orElse(0);

        boolean pb = newCatch > maxLength;
        if (pb) {
            catchRecordRepository.setPersonalBestFalse(fishId);
        }

        return pb;
    }
}
