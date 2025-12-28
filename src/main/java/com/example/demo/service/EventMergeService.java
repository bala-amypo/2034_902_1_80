package com.example.demo.service;

import com.example.demo.entity.EventMergeRecord;
import java.time.LocalDate;
import java.util.List;

public interface EventMergeService {
    EventMergeRecord mergeEvents(List<Long> eventIds, String reason);
    List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end);
    EventMergeRecord getMergeRecordById(Long id);
}