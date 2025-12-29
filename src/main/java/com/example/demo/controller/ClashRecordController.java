package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.ClashRecord;
import com.example.demo.service.impl.ClashDetectionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clashes")
@Tag(name = "Clash Records", description = "Manage event clashes")
public class ClashRecordController {

    private final ClashDetectionServiceImpl clashDetectionService;

    public ClashRecordController(ClashDetectionServiceImpl clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }

    @PostMapping
    @Operation(summary = "Log a new clash")
    public ResponseEntity<ApiResponse> logClash(@RequestBody ClashRecord clash) {
        ClashRecord logged = clashDetectionService.logClash(clash);
        return new ResponseEntity<>(new ApiResponse(true, "Clash logged", logged), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve a clash")
    public ResponseEntity<ApiResponse> resolveClash(@PathVariable Long id) {
        ClashRecord resolved = clashDetectionService.resolveClash(id);
        return ResponseEntity.ok(new ApiResponse(true, "Clash resolved", resolved));
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get clashes for a specific event")
    public ResponseEntity<ApiResponse> getClashesForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(new ApiResponse(true, "Clashes fetched", clashDetectionService.getClashesForEvent(eventId)));
    }

    @GetMapping("/unresolved")
    @Operation(summary = "Get all unresolved clashes")
    public ResponseEntity<ApiResponse> getUnresolvedClashes() {
        return ResponseEntity.ok(new ApiResponse(true, "Unresolved clashes fetched", clashDetectionService.getUnresolvedClashes()));
    }

    @GetMapping
    @Operation(summary = "Get all clashes")
    public ResponseEntity<ApiResponse> getAllClashes() {
        return ResponseEntity.ok(new ApiResponse(true, "All clashes fetched", clashDetectionService.getAllClashes()));
    }
}