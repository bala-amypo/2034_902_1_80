package com.example.demo.controller;

import com.example.demo.entity.EventMergeRecord;
import com.example.demo.service.EventMergeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/merge-records")
public class EventMergeController {
    private final EventMergeService eventMergeService;

    public EventMergeController(EventMergeService eventMergeService) {
        this.eventMergeService = eventMergeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventMergeRecord> getMergeRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(eventMergeService.getMergeRecordById(id));
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