package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.impl.HarmonizedCalendarServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/harmonized-calendars")
@Tag(name = "Harmonized Calendars", description = "Manage final calendars")
public class HarmonizedCalendarController {

    private final HarmonizedCalendarServiceImpl harmonizedCalendarService;

    public HarmonizedCalendarController(HarmonizedCalendarServiceImpl harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

   
    public static class GenerateRequestDto {
        public String title;
        public String generatedBy;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate a new harmonized calendar")
    public ResponseEntity<ApiResponse> generateCalendar(@RequestBody GenerateRequestDto request) {
        HarmonizedCalendar calendar = harmonizedCalendarService.generateHarmonizedCalendar(request.title, request.generatedBy);
        return new ResponseEntity<>(new ApiResponse(true, "Calendar generated", calendar), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get calendar by ID")
    public ResponseEntity<ApiResponse> getCalendarById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(true, "Calendar fetched", harmonizedCalendarService.getCalendarById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all calendars")
    public ResponseEntity<ApiResponse> getAllCalendars() {
        return ResponseEntity.ok(new ApiResponse(true, "All calendars fetched", harmonizedCalendarService.getAllCalendars()));
    }

    @GetMapping("/range")
    @Operation(summary = "Get calendars within effective range")
    public ResponseEntity<ApiResponse> getCalendarsWithinRange(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return ResponseEntity.ok(new ApiResponse(true, "Calendars fetched", harmonizedCalendarService.getCalendarsWithinRange(start, end)));
    }
}