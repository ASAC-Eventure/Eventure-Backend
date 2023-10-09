package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class EventsController {



    @Autowired
    AddEventJPARepository addEventJPARepository;
    @Autowired
    private EventsJPARepository eventsJPARepository;
    @Autowired
    private EventService eventService;
    @Value("${apiSecretKey}")
    String myKey;

    @GetMapping("/events")
    public String showEvents(@RequestParam(name = "searchCriteria", required = false) String searchCriteria, Model m) {
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            // Split the input into words
            String[] words = searchCriteria.trim().split("\\s+");
            // Initialize variables for countryName and startDate
            String countryName = null;
            LocalDate startDate = null;
            // Loop through the words in the input
            for (String word : words) {
                // Attempt to parse each word as a date
                try {
                    LocalDate parsedDate = LocalDate.parse(word, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    // If parsing succeeds, assume it's a date
                    if (startDate == null) {
                        startDate = parsedDate;
                    }
                } catch (DateTimeParseException ignored) {
                    // If parsing as a date fails, assume it's part of the country name
                    if (countryName == null) {
                        countryName = word;
                    } else {
                        // If there are multiple words for country name, combine them
                        countryName += " " + word;
                    }
                }
            }
            // Perform the appropriate search based on countryName and startDate
            if (countryName != null && !countryName.isEmpty()) {
                Events countryEvents = eventService.getEvents_By_CountryName(countryName);
                m.addAttribute("events", countryEvents.getEvents());
            }
            if (startDate != null) {
                Events dateEvents = eventService.getEvents_By_startDate(startDate);
                m.addAttribute("events", dateEvents.getEvents());
            }
        }
        return "searchedEvents.html";
    }


    @GetMapping("/addNewEvent")
    public String addEventData(){
        return "contact";
    }
    @PostMapping("/addNewEvent")
    public RedirectView addEventData(@RequestParam String name,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                     @RequestParam String eventUrl,
                                     @RequestParam String imageUrl,
                                     @RequestParam String location,
                                     @RequestParam String streetAddress,
                                     @RequestParam int price,
                                     @RequestParam String time,
                                     RedirectAttributes redir) {
        AddEventEntity newEvent = new AddEventEntity(name, startDate, endDate, eventUrl, location, streetAddress, price, imageUrl, false, false,time);
        AddEventEntity existingEvent = addEventJPARepository.findByName(name);

        if (existingEvent == null) {
            redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
            addEventJPARepository.save(newEvent);
        } else {
            redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Saved!");

        }
        return new RedirectView("/aboutUs");

    }
}
