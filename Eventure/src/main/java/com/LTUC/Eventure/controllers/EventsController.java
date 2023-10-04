package com.LTUC.Eventure.controllers;

//import com.LTUC.Eventure.models.AppUserEntity;
//import com.LTUC.Eventure.models.CommentSectionEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
//import com.LTUC.Eventure.repositories.CommentSectionJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventsController {

    @Autowired
    EventService eventService;
    @Autowired
    EventsJPARepository theEventJPA;
    @Autowired
    AppUserJPARepository appUserRepository;

    @Value("${apiSecretKey}")
    String myKey;

        @Transactional
        @GetMapping("/events")
        public String showEvents(@RequestParam(required = false) String countryName, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, Model m) {
            if (countryName != null) {
                String countryISO2 = "";
                String[] countryNameArr = countryName.toUpperCase().split(" ");
                if (countryNameArr.length >= 2)
                    countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
                else
                    countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
                System.out.println(countryISO2);
                String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=357b5a27-55f2-487b-9b1c-83f6ad689c3e&geoCountryIso2=" + countryISO2;
                eventService.fetchAndSaveEventsFromApi(apiData);

                List<Event> countryEvents = theEventJPA.findAll();
                m.addAttribute("countryEvents", countryEvents);
            }
            if (startDate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String startDateString = startDate.format(formatter);
                String apiDataForDate = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&eventDateFrom=" + startDateString;
                eventService.fetchAndSaveEventsFromApi(apiDataForDate);
                List<Event> dateEvents = theEventJPA.findAll();
                m.addAttribute("dateEvents", dateEvents);
            }else {
                String apiDataForRandom = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey;
                eventService.fetchAndSaveEventsFromApi(apiDataForRandom);
                List<Event> events = theEventJPA.findAll();
                List<Event> mostRatedEvents = events.stream().limit(10).collect(Collectors.toList());
                m.addAttribute("mostRatedEvents", mostRatedEvents);
            }
            return "events.html";
        }


//    @GetMapping("/eventDetails/{id}")
//    public String showDetails(@PathVariable Long id, Model model) {
//        Event event = theEventJPA.findById(id).orElseThrow();
//        if (event != null) {
//            model.addAttribute("event", event);
//        }
//        return "eventDetails.html";
//    }
}
