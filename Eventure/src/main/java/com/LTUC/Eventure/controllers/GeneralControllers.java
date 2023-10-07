package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
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
    EventService eventService;
    @Value("${apiSecretKey}")
    String myKey;

    @Autowired
    AddEventJPARepository addEventJPARepository;

    @GetMapping("/")
    public String Home(Model m) {
        // Switching navbar based on authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
            m.addAttribute("isUsernameFound", "no");
        } else {
            m.addAttribute("isUsernameFound", "yes");
        }

        List<Event> mostRatedEvents= eventService.get10RandomEvents();
        m.addAttribute("mostRatedEvents", mostRatedEvents);

        List<AddEventEntity> approvedEvents = addEventJPARepository.findAll().stream().filter(s->s.isApproved()==true && s.getUser()==null).collect(Collectors.toList());
        System.out.println("approved e"+approvedEvents.size());

        m.addAttribute("approvedEvents", approvedEvents);
        return "index";
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
        return "about.html";
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
