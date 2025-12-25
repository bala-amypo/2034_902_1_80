
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.HarmonizedCalendar;
import com.example.demo.service.HarmonizedCalendarService;
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
@RequestMapping("/api/harmonized-calendars")
@Tag(name = "Harmonized Calendars")
public class HarmonizedCalendarController {
    
    private final HarmonizedCalendarService harmonizedCalendarService;

    public HarmonizedCalendarController(HarmonizedCalendarService harmonizedCalendarService) {
        this.harmonizedCalendarService = harmonizedCalendarService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate harmonized calendar", description = "Generate a new harmonized calendar")
    public ResponseEntity<ApiResponse<HarmonizedCalendar>> generateHarmonizedCalendar(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String generatedBy = request.get("generatedBy");
        
        HarmonizedCalendar calendar = harmonizedCalendarService.generateHarmonizedCalendar(title, generatedBy);
        return ResponseEntity.ok(new ApiResponse<>(true, "Harmonized calendar generated successfully", calendar));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get calendar by ID", description = "Get harmonized calendar by ID")
    public ResponseEntity<ApiResponse<HarmonizedCalendar>> getCalendarById(@Parameter(name = "id", description = "Calendar ID") @PathVariable Long id) {
        HarmonizedCalendar calendar = harmonizedCalendarService.getCalendarById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Calendar retrieved successfully", calendar));
    }

    @GetMapping
    @Operation(summary = "Get all calendars", description = "Get list of all harmonized calendars")
    public ResponseEntity<ApiResponse<List<HarmonizedCalendar>>> getAllCalendars() {
        List<HarmonizedCalendar> calendars = harmonizedCalendarService.getAllCalendars();
        return ResponseEntity.ok(new ApiResponse<>(true, "Calendars retrieved successfully", calendars));
    }

    @GetMapping("/range")
    @Operation(summary = "Get calendars by date range", description = "Get calendars within a date range")
    public ResponseEntity<ApiResponse<List<HarmonizedCalendar>>> getCalendarsWithinRange(
            @Parameter(name = "start", description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(name = "end", description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<HarmonizedCalendar> calendars = harmonizedCalendarService.getCalendarsWithinRange(start, end);
        return ResponseEntity.ok(new ApiResponse<>(true, "Calendars retrieved successfully", calendars));
    }
}
