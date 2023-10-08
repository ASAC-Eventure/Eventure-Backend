package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EventsController {
    private EventService eventService;

    @Autowired
    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/events")
    public String showEvents(@RequestParam(name = "countryName", required = false) String countryName,
                             @RequestParam(name = "startDate", required = false)
                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, Model m) {
        if (countryName.length() > 0 && startDate != null) {
            System.out.println("inside both conditions");
            Events country_dateEvents = eventService.getEvents_By_CountryName_and_startDate(countryName, startDate);
            m.addAttribute("events", country_dateEvents.getEvents());
        } else if (countryName.length() > 0) {
            System.out.println("inside country condition");
            Events countryEvents = eventService.getEvents_By_CountryName(countryName);
            m.addAttribute("events", countryEvents.getEvents());
        } else if (startDate != null) {
            System.out.println("inside date condition");
            Events dateEvents = eventService.getEvents_By_startDate(startDate);
            m.addAttribute("events", dateEvents.getEvents());
        }
        return "searchedEvents.html";
    }

}
