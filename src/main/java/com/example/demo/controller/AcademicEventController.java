
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.AcademicEvent;
import com.example.demo.service.AcademicEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Academic Events")
public class AcademicEventController {
    
    private final AcademicEventService academicEventService;

    public AcademicEventController(AcademicEventService academicEventService) {
        this.academicEventService = academicEventService;
    }

    @PostMapping
    @Operation(summary = "Create event", description = "Create a new academic event")
    public ResponseEntity<ApiResponse<AcademicEvent>> createEvent(@RequestBody AcademicEvent event) {
        AcademicEvent createdEvent = academicEventService.createEvent(event);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event created successfully", createdEvent));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update event", description = "Update an existing academic event")
    public ResponseEntity<ApiResponse<AcademicEvent>> updateEvent(
            @Parameter(name = "id", description = "Event ID") @PathVariable Long id,
            @RequestBody AcademicEvent event) {
        AcademicEvent updatedEvent = academicEventService.updateEvent(id, event);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event updated successfully", updatedEvent));
    }

    @GetMapping("/branch/{branchId}")
    @Operation(summary = "Get events by branch", description = "Get all events for a specific branch")
    public ResponseEntity<ApiResponse<List<AcademicEvent>>> getEventsByBranch(@Parameter(name = "branchId", description = "Branch ID") @PathVariable Long branchId) {
        List<AcademicEvent> events = academicEventService.getEventsByBranch(branchId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Events retrieved successfully", events));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Get academic event by ID")
    public ResponseEntity<ApiResponse<AcademicEvent>> getEventById(@Parameter(name = "id", description = "Event ID") @PathVariable Long id) {
        AcademicEvent event = academicEventService.getEventById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event retrieved successfully", event));
    }

    @GetMapping
    @Operation(summary = "Get all events", description = "Get list of all academic events")
    public ResponseEntity<ApiResponse<List<AcademicEvent>>> getAllEvents() {
        List<AcademicEvent> events = academicEventService.getAllEvents();
        return ResponseEntity.ok(new ApiResponse<>(true, "Events retrieved successfully", events));
    }
}