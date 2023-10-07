package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Controller
public class EventsController {

    @Autowired
    private EventsJPARepository eventsJPARepository;
    @Autowired
    private EventService eventService;
    @Value("${apiSecretKey}")
    String myKey;

    @GetMapping("/events")
    public String showEvents(@RequestParam(name = "countryName", required = false) String countryName,
                             @RequestParam(name = "startDate", required = false)
                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, Model m) {
        if (countryName.length()>0 && startDate !=null){
            System.out.println("inside both conditions");
            String countryISO2 = "";
            String[] countryNameArr = countryName.toUpperCase().trim().split(" ");
            if (countryNameArr.length >= 2)
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
            else
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
            System.out.println(countryISO2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateString = startDate.format(formatter);
            String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&geoCountryIso2=" + countryISO2 +"&eventDateFrom=" + startDateString;
            Events  country_dateEvents=  eventService.fetchAndSaveEventsFromApi(apiData);
            m.addAttribute("events", country_dateEvents.getEvents());
        }
        else if (countryName.length()>0) {
            System.out.println("inside country condition");
            String countryISO2 = "";
            String[] countryNameArr = countryName.toUpperCase().trim().split(" ");
            if (countryNameArr.length >= 2)
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
            else
                countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
            System.out.println(countryISO2);
            String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&geoCountryIso2=" + countryISO2 ;
            Events countryEvents= eventService.fetchAndSaveEventsFromApi(apiData);

            m.addAttribute("events", countryEvents.getEvents());
        }
       else if (startDate != null) {
            System.out.println("inside date condition");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateString = startDate.format(formatter);
            String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&eventDateFrom=" + startDateString;
            Events dateEvents= eventService.fetchAndSaveEventsFromApi(apiData);

            m.addAttribute("events", dateEvents.getEvents());
        }
        return "Events.html";
    }

}
