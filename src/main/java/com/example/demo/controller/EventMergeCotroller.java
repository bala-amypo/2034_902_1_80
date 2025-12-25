
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
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
    @Operation(summary = "Merge events", description = "Merge multiple events into a single record")
    public ResponseEntity<ApiResponse<EventMergeRecord>> mergeEvents(@RequestBody Map<String, Object> request) {
        @SuppressWarnings("unchecked")
        List<Long> eventIds = (List<Long>) request.get("eventIds");
        String reason = (String) request.get("reason");
        
        EventMergeRecord mergeRecord = eventMergeService.mergeEvents(eventIds, reason);
        return ResponseEntity.ok(new ApiResponse<>(true, "Events merged successfully", mergeRecord));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get merge record by ID", description = "Get event merge record by ID")
    public ResponseEntity<ApiResponse<EventMergeRecord>> getMergeRecordById(@Parameter(name = "id", description = "Merge record ID") @PathVariable Long id) {
        EventMergeRecord mergeRecord = eventMergeService.getMergeRecordById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Merge record retrieved successfully", mergeRecord));
    }

    @GetMapping
    @Operation(summary = "Get all merge records", description = "Get list of all event merge records")
    public ResponseEntity<ApiResponse<List<EventMergeRecord>>> getAllMergeRecords() {
        List<EventMergeRecord> mergeRecords = eventMergeService.getAllMergeRecords();
        return ResponseEntity.ok(new ApiResponse<>(true, "Merge records retrieved successfully", mergeRecords));
    }

    @GetMapping("/range")
    @Operation(summary = "Get merge records by date range", description = "Get merge records within a date range")
    public ResponseEntity<ApiResponse<List<EventMergeRecord>>> getMergeRecordsByDate(
            @Parameter(name = "start", description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(name = "end", description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<EventMergeRecord> mergeRecords = eventMergeService.getMergeRecordsByDate(start, end);
        return ResponseEntity.ok(new ApiResponse<>(true, "Merge records retrieved successfully", mergeRecords));
    }
}