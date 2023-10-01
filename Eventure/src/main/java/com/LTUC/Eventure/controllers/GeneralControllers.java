package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apimodels.Event;
import com.LTUC.Eventure.repositories.TheEventJPA;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GeneralControllers {
    @Autowired
    TheEventJPA theEventJPA;
@Autowired
EventService eventService;

        @GetMapping("/events")
        public String showIndex( String countryName, Model m){
            if(countryName!=null){
            String countryISO2="";
                    String []countryNameArr  = countryName.toUpperCase().split(" ");
                    if(countryNameArr.length>=2)
                        countryISO2 = String.valueOf(countryNameArr[0].charAt(0))+countryNameArr[1].charAt(0);
                    else
                        countryISO2=String.valueOf(countryNameArr[0].charAt(0))+countryNameArr[0].charAt(1);
            System.out.println(countryISO2);
            String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=357b5a27-55f2-487b-9b1c-83f6ad689c3e&geoCountryIso2="+countryISO2;
            eventService.fetchAndSaveEventsFromApi(apiData);

            List<Event> events= theEventJPA.findAll();
            m.addAttribute("events",events);}
            return "index.html";
        }
//@GetMapping("/api")
//    public List<Event> fetchAndSaveEvents() {
//   return theEventJPA.findByLocation_Address_AddressRegion_Name("North Carolina");
//    }
    }

