package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralControllers {
    @Autowired
    EventService eventService;
    String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=357b5a27-55f2-487b-9b1c-83f6ad689c3e&page=1";
        @GetMapping("/")
        @ResponseBody
        public String showIndex(){


            eventService.fetchAndSaveEventsFromApi(apiData);
            return "Welcome";
        }
//@GetMapping("/api")
//    public void fetchAndSaveEvents() {
//
//    }
    }

