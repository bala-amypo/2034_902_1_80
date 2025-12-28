package com.example.demo.service;

import com.example.demo.entity.HarmonizedCalendar;
import java.time.LocalDate;
import java.util.List;

public interface HarmonizedCalendarService {
    HarmonizedCalendar generateHarmonizedCalendar(String title, String generatedBy);
    List<HarmonizedCalendar> getCalendarsWithinRange(LocalDate start, LocalDate end);
    HarmonizedCalendar getCalendarById(Long id);
}