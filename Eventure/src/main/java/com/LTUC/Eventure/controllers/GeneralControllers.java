package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GeneralControllers {
    @Autowired
    AppUserJPARepository appUserJPARepository;
    @Autowired
    EventsJPARepository eventsJPARepository;
    @Autowired
    EventService eventService;
    @Value("${apiSecretKey}")
    String myKey;

    @GetMapping("/")
    public String Home(Model m) {

        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey;
        eventService.fetchAndSaveEventsFromApi(apiData);
        List<Event> events = eventsJPARepository.findAll();
        List<Event> mostRatedEvents = events.stream().limit(10).collect(Collectors.toList());
        m.addAttribute("mostRatedEvents", mostRatedEvents);
        return "index";
    }

    @GetMapping("/home")
    public String securedHome() {
        return "indexAfter";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username.equals("anonymousUser")) {
            model.addAttribute("isUsernameFound", "no");
        } else {
            model.addAttribute("isUsernameFound", "yes");
        }
        return "aboutUs.html";
    }


    @GetMapping("/terms-conditions")
    public String termsAndCondintions() {
        return "T&C.html";
    }

    @GetMapping("/logout")
    public RedirectView logOut() {
        return new RedirectView("/");
    }
}
