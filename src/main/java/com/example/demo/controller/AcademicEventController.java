package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.impl.AcademicEventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Academic Events", description = "Manage academic events")
public class AcademicEventController {

    private final AcademicEventServiceImpl academicEventService;

    public AcademicEventController(AcademicEventServiceImpl academicEventService) {
        this.academicEventService = academicEventService;
    }

    @PostMapping
    @Operation(summary = "Create a new event")
    public ResponseEntity<ApiResponse> createEvent(@RequestBody AcademicEvent event) {
        AcademicEvent created = academicEventService.createEvent(event);
        return new ResponseEntity<>(new ApiResponse(true, "Event created", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing event")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable Long id, @RequestBody AcademicEvent event) {
        AcademicEvent updated = academicEventService.updateEvent(id, event);
        return ResponseEntity.ok(new ApiResponse(true, "Event updated", updated));
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get events for a specific branch")
    public ResponseEntity<ApiResponse> getEventsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(new ApiResponse(true, "Events fetched", academicEventService.getEventsByBranch(branchId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID")
    public ResponseEntity<ApiResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(true, "Event fetched", academicEventService.getEventById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all events")
    public ResponseEntity<ApiResponse> getAllEvents() {
        return ResponseEntity.ok(new ApiResponse(true, "All events fetched", academicEventService.getAllEvents()));
    }
}