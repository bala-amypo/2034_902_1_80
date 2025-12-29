package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.repository.EventMergeRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMergeServiceImpl {

    private final EventMergeRecordRepository eventMergeRecordRepository;
    private final AcademicEventRepository academicEventRepository;

    public EventMergeServiceImpl(EventMergeRecordRepository eventMergeRecordRepository, AcademicEventRepository academicEventRepository) {
        this.eventMergeRecordRepository = eventMergeRecordRepository;
        this.academicEventRepository = academicEventRepository;
    }

    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {
      
        List<AcademicEvent> events = academicEventRepository.findAllById(eventIds);

       
        if (events.isEmpty() || events.size() != eventIds.size()) {
            throw new ResourceNotFoundException("No events found or some provided IDs are invalid");
        }

        LocalDate minStart = events.stream()
                .map(AcademicEvent::getStartDate)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());

        LocalDate maxEnd = events.stream()
                .map(AcademicEvent::getEndDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

      
        EventMergeRecord record = new EventMergeRecord();
        String commaSeparatedIds = events.stream()
                .map(e -> String.valueOf(e.getId()))
                .collect(Collectors.joining(","));
        
        record.setSourceEventIds(commaSeparatedIds);
        record.setMergedTitle("Merged Event (" + events.size() + " items)");
        record.setMergedStartDate(minStart);
        record.setMergedEndDate(maxEnd);
        record.setMergeReason(reason);

        return eventMergeRecordRepository.save(record);
    }

    public List<EventMergeRecord> getAllMergeRecords() {
        return eventMergeRecordRepository.findAll();
    }

    public EventMergeRecord getMergeRecordById(Long id) {
        return eventMergeRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Merge record not found with ID: " + id));
    }

    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return eventMergeRecordRepository.findByMergedStartDateBetween(start, end);
    }
}