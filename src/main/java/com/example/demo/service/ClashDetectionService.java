package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ClashRecord;

public interface ClashDetectionService {

    ClashRecord logClash(ClashRecord clash);

    List<ClashRecord> getClashesForEvent(Long eventId);

    ClashRecord resolveClash(Long clashId);

    List<ClashRecord> getUnresolvedClashes();

    List<ClashRecord> getAllClashes();
}
