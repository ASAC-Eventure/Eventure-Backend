package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class EventsController {
    private EventService eventService;

    @Autowired
    AddEventJPARepository addEventJPARepository;
    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public String showEvents(@RequestParam(name = "searchCriteria", required = false) String searchCriteria, Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            m.addAttribute("isUsernameFound", "no");
        } else {
            m.addAttribute("isUsernameFound", "yes");
        }

        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            String[] words = searchCriteria.trim().split("\\s+");
            String countryName = null;
            LocalDate startDate = null;

            for (String word : words) {
                try {
                    LocalDate parsedDate = LocalDate.parse(word, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (startDate == null) {
                        startDate = parsedDate;
                    }
                } catch (DateTimeParseException ignored) {
                    if (countryName == null) {
                        countryName = word;
                    } else {
                        countryName += " " + word;
                    }
                }
            }

            if (countryName != null && !countryName.isEmpty() && startDate != null) {
                System.out.println("reached both");
                Events countryDateEvents = eventService.getEvents_By_CountryName_and_startDate(countryName, startDate);
                if (countryDateEvents != null) {
                    System.out.println("finded all");
                    m.addAttribute("events", countryDateEvents.getEvents());
                } else {
                    m.addAttribute("noEventsMessage", "No events found for the specified country and date.");
                }
            } else if (countryName != null && !countryName.isEmpty()) {
                System.out.println("reached country");
                Events countryEvents = eventService.getEvents_By_CountryName(countryName);
                if (countryEvents != null) {
                    System.out.println("finded country");
                    m.addAttribute("events", countryEvents.getEvents());
                } else {
                    m.addAttribute("noEventsMessage", "No events found for the specified country.");
                }
            } else if (startDate != null) {
                System.out.println("reached start date");
                Events dateEvents = eventService.getEvents_By_startDate(startDate);
                if (dateEvents != null) {
                    System.out.println("finded start date");
                    m.addAttribute("events", dateEvents.getEvents());
                } else {
                    m.addAttribute("noEventsMessage", "No events found for the specified date.");
                }
            }
        }
        return "searchedEvents.html";
    }
}