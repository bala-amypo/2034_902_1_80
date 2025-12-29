package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchProfileController {
    private final BranchProfileService branchProfileService;

    public BranchProfileController(BranchProfileService branchProfileService) {
        this.branchProfileService = branchProfileService;
    }

    @GetMapping
    public ResponseEntity<List<BranchProfile>> getAllBranches() {
        return ResponseEntity.ok(branchProfileService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchProfile> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchProfileService.getBranchById(id));
    }

    @PostMapping
    public ResponseEntity<BranchProfile> createBranch(@RequestBody BranchProfile branch) {
        return ResponseEntity.ok(branchProfileService.createBranch(branch));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BranchProfile> updateBranchStatus(@PathVariable Long id, @RequestParam Boolean active) {
        return ResponseEntity.ok(branchProfileService.updateBranchStatus(id, active));
    }
}