package com.example.demo.controller;

import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/harmonized-calendars")
public class HarmonizedCalendarController {
    private final HarmonizedCalendarService harmonizedCalendarService;

    public HarmonizedCalendarController(HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HarmonizedCalendar> getCalendarById(@PathVariable Long id) {
        return ResponseEntity.ok(harmonizedCalendarService.getCalendarById(id));
    }

    @PostMapping
    public ResponseEntity<HarmonizedCalendar> generateCalendar(@RequestParam String title, @RequestParam String generatedBy) {
        return ResponseEntity.ok(harmonizedCalendarService.generateHarmonizedCalendar(title, generatedBy));
    }

    @GetMapping("/range")
    public ResponseEntity<List<HarmonizedCalendar>> getCalendarsInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(harmonizedCalendarService.getCalendarsWithinRange(start, end));
    }
}