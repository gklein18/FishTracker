package com.app.FishTracker.controller;

import com.app.FishTracker.dto.catchrecord.CatchRecordDTO;
import com.app.FishTracker.dto.catchrecord.CreateCatchRequest;
import com.app.FishTracker.dto.catchrecord.UpdateCatchRequest;
import com.app.FishTracker.model.CatchRecord;
import com.app.FishTracker.service.CatchRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/catch")
public class CatchRecordController {

    private final CatchRecordService catchRecordService;

    public CatchRecordController(CatchRecordService catchRecordService) {
        this.catchRecordService = catchRecordService;
    }

    @GetMapping
    public List<CatchRecordDTO> getAllCatches() {
        return catchRecordService.getAllCatches();
    }

    @GetMapping("/user/{id}")
    public List<CatchRecordDTO> getUserCatches(@PathVariable Long id) {
        return catchRecordService.findByUser(id);
    }

    @PostMapping("/create")
    public CatchRecordDTO createCatchRecord(@RequestBody CreateCatchRequest request) {
        return catchRecordService.createCatchRecord(request);
    }

    @PostMapping("/bulk")
    public List<CatchRecordDTO> createCatchRecordBatch(@RequestBody List<CreateCatchRequest> catchRecords) {
        List<CatchRecordDTO> retList = new ArrayList<>();

        for (CreateCatchRequest record : catchRecords) {
            retList.add(catchRecordService.createCatchRecord(record));
        }

        return retList;
    }

    @PutMapping("/update/{tripId}")
    public CatchRecordDTO updateCatchRecord(@RequestBody UpdateCatchRequest catchRecord, @PathVariable Long tripId) {
        return catchRecordService.updateCatchRecord(catchRecord, tripId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCatchRecord(@PathVariable Long id) {
        catchRecordService.deleteCatchRecord(id);
    }

    @GetMapping("/{id}")
    public CatchRecordDTO getCatchById(@PathVariable Long id) {
        return catchRecordService.getCatchRecordById(id);
    }

    @GetMapping("/trip/{id}")
    public List<CatchRecordDTO> getCatchesByTripId(@PathVariable Long id) {
        return catchRecordService.findByTripId(id);
    }

    @GetMapping("/recent/{id}")
    public List<CatchRecordDTO> getRecentCatches(@PathVariable Long id) {
        return catchRecordService.getRecentCatches(id);
    }

    @GetMapping("/pb/{id}")
    public List<CatchRecordDTO> getRecentPBs(@PathVariable Long id) {
        return catchRecordService.getRecentPersonalBests(id);
    }

}
