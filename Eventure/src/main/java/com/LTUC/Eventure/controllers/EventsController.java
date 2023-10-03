package com.LTUC.Eventure.controllers;

<<<<<<< HEAD
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.CommentSectionJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class EventsController {
    private EventsJPARepository eventsJPARepository;
    private EventService eventService;
    @Autowired
    AppUserJPARepository appUserRepository;

    @Autowired
    CommentSectionJPARepository commentSectionJPARepository;
    @Autowired
    public EventsController(EventsJPARepository eventsJPARepository, EventService eventService) {
        this.eventsJPARepository = eventsJPARepository;
        this.eventService = eventService;
    }
    @Value("${apiSecretKey}")
    String myKey;


    @GetMapping("/events")
    public String showEvents( String countryName, Model m){
        //====================== getting events from searchbar by country
        if(countryName!=null) {
            String countryISO2 = "";
            String[] countryNameArr = countryName.toUpperCase().split(" ");
            if (countryNameArr.length >= 2)
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
            else
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
            System.out.println(countryISO2);
            String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&geoCountryIso2=" + countryISO2;
            eventService.fetchAndSaveEventsFromApi(apiData);
            List<Event> events = eventsJPARepository.findAll();
            m.addAttribute("events", events);

        }
        return "events.html";
    }

<<<<<<< HEAD
=======
    @GetMapping("/eventDetails/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Event event = theEventJPA.findById(id).orElseThrow();
        if (event != null) {
            model.addAttribute("event", event);
            List<CommentSectionEntity> comments = commentSectionJPARepository.findByEvent(event);
            model.addAttribute("comments", comments);
        }
        return "eventDetails.html";
    }
>>>>>>> origin/reneh-comment
}
