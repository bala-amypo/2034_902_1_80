package com.example.demo.service;

import com.example.demo.entity.BranchProfile;
import java.util.List;

public interface BranchProfileService {
    BranchProfile createBranch(BranchProfile branch);
    BranchProfile updateBranchStatus(Long id, Boolean active);
    List<BranchProfile> getAllBranches();
    BranchProfile getBranchById(Long id);
}