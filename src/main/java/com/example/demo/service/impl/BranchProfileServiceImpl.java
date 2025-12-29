package com.example.demo.service.impl;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BranchProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchProfileServiceImpl {

    private final BranchProfileRepository branchProfileRepository;

    public BranchProfileServiceImpl(BranchProfileRepository branchProfileRepository) {
        this.branchProfileRepository = branchProfileRepository;
    }

    public BranchProfile createBranch(BranchProfile branch) {
       
        return branchProfileRepository.save(branch);
    }

    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile branch = getBranchById(id);
        branch.setActive(active);
        return branchProfileRepository.save(branch);
    }

    public List<BranchProfile> getAllBranches() {
        return branchProfileRepository.findAll();
    }

    public BranchProfile getBranchById(Long id) {
        return branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + id));
    }

    public BranchProfile findByBranchCode(String branchCode) {
        return branchProfileRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with code: " + branchCode));
    }
}