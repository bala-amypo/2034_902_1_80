package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.impl.EventMergeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
@Tag(name = "Event Merge Records", description = "Manage event merging")
public class EventMergeController {

    private final EventMergeServiceImpl eventMergeService;

    public EventMergeController(EventMergeServiceImpl eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

   
    public static class MergeRequestDto {
        public List<Long> eventIds;
        public String reason;
    }

    @PostMapping
    @Operation(summary = "Merge multiple events")
    public ResponseEntity<ApiResponse> mergeEvents(@RequestBody MergeRequestDto request) {
        EventMergeRecord record = eventMergeService.mergeEvents(request.eventIds, request.reason);
        return new ResponseEntity<>(new ApiResponse(true, "Events merged", record), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get merge record by ID")
    public ResponseEntity<ApiResponse> getMergeRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(true, "Record fetched", eventMergeService.getMergeRecordById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all merge records")
    public ResponseEntity<ApiResponse> getAllMergeRecords() {
        return ResponseEntity.ok(new ApiResponse(true, "All records fetched", eventMergeService.getAllMergeRecords()));
    }

    @GetMapping("/range")
    @Operation(summary = "Get merge records within date range")
    public ResponseEntity<ApiResponse> getMergeRecordsByDate(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return ResponseEntity.ok(new ApiResponse(true, "Records fetched", eventMergeService.getMergeRecordsByDate(start, end)));
    }
}