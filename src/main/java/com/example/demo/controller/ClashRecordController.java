
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<ClashRecord>> logClash(@RequestBody ClashRecord clash) {
        ClashRecord loggedClash = clashDetectionService.logClash(clash);
        return ResponseEntity.ok(new ApiResponse<>(true, "Clash logged successfully", loggedClash));
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Resolve clash", description = "Mark a clash as resolved")
    public ResponseEntity<ApiResponse<ClashRecord>> resolveClash(@Parameter(name = "id", description = "Clash ID") @PathVariable Long id) {
        ClashRecord resolvedClash = clashDetectionService.resolveClash(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Clash resolved successfully", resolvedClash));
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get clashes for event", description = "Get all clashes involving a specific event")
    public ResponseEntity<ApiResponse<List<ClashRecord>>> getClashesForEvent(@Parameter(name = "eventId", description = "Event ID") @PathVariable Long eventId) {
        List<ClashRecord> clashes = clashDetectionService.getClashesForEvent(eventId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Clashes retrieved successfully", clashes));
    }

    @GetMapping("/unresolved")
    @Operation(summary = "Get unresolved clashes", description = "Get all unresolved clash records")
    public ResponseEntity<ApiResponse<List<ClashRecord>>> getUnresolvedClashes() {
        List<ClashRecord> clashes = clashDetectionService.getUnresolvedClashes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Unresolved clashes retrieved successfully", clashes));
    }

    @GetMapping
    @Operation(summary = "Get all clashes", description = "Get list of all clash records")
    public ResponseEntity<ApiResponse<List<ClashRecord>>> getAllClashes() {
        List<ClashRecord> clashes = clashDetectionService.getAllClashes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Clashes retrieved successfully", clashes));
    }
}
