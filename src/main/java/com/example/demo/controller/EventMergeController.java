package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merge-records")
@Tag(name = "Event Merge Records")
public class EventMergeController {
    private final EventMergeService eventMergeService;
    
    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }
    
    @PostMapping
    @Operation(summary = "Merge events", description = "Merge multiple events into one record")
    public ResponseEntity<EventMergeRecord> mergeEvents(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Long> eventIds = (List<Long>) request.get("eventIds");
        String reason = (String) request.get("reason");
        return ResponseEntity.ok(eventMergeService.mergeEvents(eventIds, reason));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get merge record by ID", description = "Retrieve event merge record by ID")
    public ResponseEntity<EventMergeRecord> getMergeRecordById(@Parameter(name = "id", description = "Merge record ID") @PathVariable Long id) {
        return ResponseEntity.ok(eventMergeService.getMergeRecordById(id));
    }
    
    @GetMapping
    @Operation(summary = "Get all merge records", description = "Retrieve all event merge records")
    public ResponseEntity<List<EventMergeRecord>> getAllMergeRecords() {
        return ResponseEntity.ok(eventMergeService.getAllMergeRecords());
    }
    
    @GetMapping("/range")
    @Operation(summary = "Get merge records by date range", description = "Retrieve merge records within date range")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecordsByDate(
            @Parameter(name = "start", description = "Start date") @RequestParam LocalDate start,
            @Parameter(name = "end", description = "End date") @RequestParam LocalDate end) {
        return ResponseEntity.ok(eventMergeService.getMergeRecordsByDate(start, end));
    }
}