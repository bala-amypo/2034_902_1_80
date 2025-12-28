package com.example.demo.controller;

import com.example.demo.entity.ClashRecord;
import com.example.demo.service.impl.ClashDetectionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clashes")
public class ClashController {
    private final ClashDetectionServiceImpl clashDetectionService;

    public ClashController(ClashDetectionServiceImpl clashDetectionService) {
        this.clashDetectionService = clashDetectionService;
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<ClashRecord>> getClashesForEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(clashDetectionService.getClashesForEvent(eventId));
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<ClashRecord>> getUnresolvedClashes() {
        return ResponseEntity.ok(clashDetectionService.getUnresolvedClashes());
    }

    @PutMapping("/{clashId}/resolve")
    public ResponseEntity<ClashRecord> resolveClash(@PathVariable Long clashId) {
        return ResponseEntity.ok(clashDetectionService.resolveClash(clashId));
    }
}