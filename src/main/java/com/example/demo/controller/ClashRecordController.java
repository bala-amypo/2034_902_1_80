package com.example.demo.controller;

import com.example.demo.entity.ClashRecord;
import com.example.demo.service.ClashDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clashes")
@Tag(name = "Clash Records")
public class ClashRecordController {
    private final ClashDetectionService clashDetectionService;
    
    public ClashRecordController(ClashDetectionService clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }
    
    @PostMapping
    @Operation(summary = "Log clash", description = "Log a new clash record")
    public ResponseEntity<ClashRecord> logClash(@RequestBody ClashRecord clash) {
        return ResponseEntity.ok(clashDetectionService.logClash(clash));
    }
    
    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve clash", description = "Mark a clash as resolved")
    public ResponseEntity<ClashRecord> resolveClash(@Parameter(name = "id", description = "Clash ID") @PathVariable Long id) {
        return ResponseEntity.ok(clashDetectionService.resolveClash(id));
    }
    
    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get clashes for event", description = "Retrieve clashes for a specific event")
    public ResponseEntity<List<ClashRecord>> getClashesForEvent(@Parameter(name = "eventId", description = "Event ID") @PathVariable Long eventId) {
        return ResponseEntity.ok(clashDetectionService.getClashesForEvent(eventId));
    }
    
    @GetMapping("/unresolved")
    @Operation(summary = "Get unresolved clashes", description = "Retrieve all unresolved clashes")
    public ResponseEntity<List<ClashRecord>> getUnresolvedClashes() {
        return ResponseEntity.ok(clashDetectionService.getUnresolvedClashes());
    }
    
    @GetMapping
    @Operation(summary = "Get all clashes", description = "Retrieve all clash records")
    public ResponseEntity<List<ClashRecord>> getAllClashes() {
        return ResponseEntity.ok(clashDetectionService.getAllClashes());
    }
}