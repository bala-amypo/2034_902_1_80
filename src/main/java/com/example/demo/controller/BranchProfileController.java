package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.BranchProfile;
import com.example.demo.service.impl.BranchProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch Profiles", description = "Manage branch details")
public class BranchProfileController {

    private final BranchProfileServiceImpl branchProfileService;

    public BranchProfileController(BranchProfileServiceImpl branchProfileService) {
        this.branchProfileService = branchProfileService;
    }

    @PostMapping
    @Operation(summary = "Create a new branch")
    public ResponseEntity<ApiResponse> createBranch(@RequestBody BranchProfile branch) {
        BranchProfile created = branchProfileService.createBranch(branch);
        return new ResponseEntity<>(new ApiResponse(true, "Branch created", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update branch active status")
    public ResponseEntity<ApiResponse> updateBranchStatus(@PathVariable Long id, @RequestParam boolean active) {
        BranchProfile updated = branchProfileService.updateBranchStatus(id, active);
        return ResponseEntity.ok(new ApiResponse(true, "Branch status updated", updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID")
    public ResponseEntity<ApiResponse> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(true, "Branch fetched", branchProfileService.getBranchById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all branches")
    public ResponseEntity<ApiResponse> getAllBranches() {
        return ResponseEntity.ok(new ApiResponse(true, "All branches fetched", branchProfileService.getAllBranches()));
    }

    @GetMapping("/lookup/{branchCode}")
    @Operation(summary = "Find branch by code")
    public ResponseEntity<ApiResponse> getBranchByCode(@PathVariable String branchCode) {
        return ResponseEntity.ok(new ApiResponse(true, "Branch fetched", branchProfileService.findByBranchCode(branchCode)));
    }
}