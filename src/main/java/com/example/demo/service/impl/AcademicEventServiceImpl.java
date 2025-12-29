package com.example.demo.service.impl;

import com.example.demo.entity.AcademicEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AcademicEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicEventServiceImpl {

    private final AcademicEventRepository academicEventRepository;

    public AcademicEventServiceImpl(AcademicEventRepository academicEventRepository) {
        this.academicEventRepository = academicEventRepository;
    }

    public AcademicEvent createEvent(AcademicEvent event) {
        validateEventDates(event);
        return academicEventRepository.save(event);
    }

    public AcademicEvent updateEvent(Long id, AcademicEvent eventDetails) {
        AcademicEvent existingEvent = getEventById(id);
        
        
        existingEvent.setTitle(eventDetails.getTitle());
        existingEvent.setEventType(eventDetails.getEventType());
        existingEvent.setStartDate(eventDetails.getStartDate());
        existingEvent.setEndDate(eventDetails.getEndDate());
        existingEvent.setLocation(eventDetails.getLocation());
        existingEvent.setDescription(eventDetails.getDescription());
        
        validateEventDates(existingEvent);
        return academicEventRepository.save(existingEvent);
    }

    public List<AcademicEvent> getEventsByBranch(Long branchId) {
        return academicEventRepository.findByBranchId(branchId);
    }

    public AcademicEvent getEventById(Long id) {
        return academicEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
    }

    public List<AcademicEvent> getAllEvents() {
        return academicEventRepository.findAll();
    }

    private void validateEventDates(AcademicEvent event) {
        if (event.getStartDate() != null && event.getEndDate() != null) {
            if (event.getStartDate().isAfter(event.getEndDate())) {
                throw new ValidationException("startDate cannot be after endDate");
            }
        }
    }
}