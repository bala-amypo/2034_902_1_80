package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.impl.EventMergeServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class MergeController {
    private final EventMergeServiceImpl eventMergeService;

    public MergeController(EventMergeServiceImpl eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    @PostMapping
    public ResponseEntity<EventMergeRecord> mergeEvents(@RequestParam List<Long> eventIds, @RequestParam String reason) {
        return ResponseEntity.ok(eventMergeService.mergeEvents(eventIds, reason));
    }

    @GetMapping("/range")
    public ResponseEntity<List<EventMergeRecord>> getMergeRecordsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(eventMergeService.getMergeRecordsByDate(start, end));
    }
}