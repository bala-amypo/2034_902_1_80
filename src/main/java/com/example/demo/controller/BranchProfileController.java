
package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch Profiles")
public class BranchProfileController {
    
    private final BranchProfileService branchProfileService;

    public BranchProfileController(BranchProfileService branchProfileService) {
        this.branchProfileService = branchProfileService;
    }

    @PostMapping
    @Operation(summary = "Create branch", description = "Create a new branch profile")
    public ResponseEntity<ApiResponse<BranchProfile>> createBranch(@RequestBody BranchProfile branch) {
        BranchProfile createdBranch = branchProfileService.createBranch(branch);
        return ResponseEntity.ok(new ApiResponse<>(true, "Branch created successfully", createdBranch));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update branch status", description = "Update the active status of a branch")
    public ResponseEntity<ApiResponse<BranchProfile>> updateBranchStatus(
            @Parameter(name = "id", description = "Branch ID") @PathVariable Long id,
            @Parameter(name = "active", description = "Active status") @RequestParam boolean active) {
        BranchProfile updatedBranch = branchProfileService.updateBranchStatus(id, active);
        return ResponseEntity.ok(new ApiResponse<>(true, "Branch status updated successfully", updatedBranch));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID", description = "Get branch profile by ID")
    public ResponseEntity<ApiResponse<BranchProfile>> getBranchById(@Parameter(name = "id", description = "Branch ID") @PathVariable Long id) {
        BranchProfile branch = branchProfileService.getBranchById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Branch retrieved successfully", branch));
    }

    @GetMapping
    @Operation(summary = "Get all branches", description = "Get list of all branch profiles")
    public ResponseEntity<ApiResponse<List<BranchProfile>>> getAllBranches() {
        List<BranchProfile> branches = branchProfileService.getAllBranches();
        return ResponseEntity.ok(new ApiResponse<>(true, "Branches retrieved successfully", branches));
    }

    @GetMapping("/lookup/{branchCode}")
    @Operation(summary = "Get branch by code", description = "Get branch profile by branch code")
    public ResponseEntity<ApiResponse<BranchProfile>> getBranchByCode(@Parameter(name = "branchCode", description = "Branch code") @PathVariable String branchCode) {
        BranchProfile branch = branchProfileService.findByBranchCode(branchCode);
        return ResponseEntity.ok(new ApiResponse<>(true, "Branch retrieved successfully", branch));
    }
}

