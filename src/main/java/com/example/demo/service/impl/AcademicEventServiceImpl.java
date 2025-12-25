package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.service.AcademicEventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AcademicEventServiceImpl implements AcademicEventService {

    private final AcademicEventRepository academicEventRepository;

    public AcademicEventServiceImpl(AcademicEventRepository academicEventRepository) {
        this.academicEventRepository = academicEventRepository;
    }

    @Override
    public AcademicEvent createEvent(AcademicEvent event) {
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("startDate cannot be after endDate");
        }
        return academicEventRepository.save(event);
    }

    @Override
    public List<AcademicEvent> getEventsByBranch(Long branchId) {
        return academicEventRepository.findByBranchId(branchId);
    }

    @Override
    public AcademicEvent updateEvent(Long id, AcademicEvent event) {
        AcademicEvent existingEvent = academicEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("startDate cannot be after endDate");
        }
        
        existingEvent.setTitle(event.getTitle());
        existingEvent.setEventType(event.getEventType());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setDescription(event.getDescription());
        
        return academicEventRepository.save(existingEvent);
    }

    @Override
    public AcademicEvent getEventById(Long id) {
        return academicEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public List<AcademicEvent> getAllEvents() {
        return academicEventRepository.findAll();
    }
}

