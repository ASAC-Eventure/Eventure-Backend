package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.CommentSectionJPARepository;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.CommentSectionEntity;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class CommentSectionController {

    @Autowired
    private CommentSectionJPARepository commentSectionRepository;
    @Autowired
    private AppUserJPARepository appUserRepository;
    @Autowired
    private EventsJPARepository eventsJPARepository;

    @PostMapping("/submit-comment")
    public String postComment(@RequestParam("message") String message, @RequestParam("id") Long id, Principal principal, Model model) {
        String username = principal.getName();
        AppUserEntity user = appUserRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());

        Event event = eventsJPARepository.findById(id).orElseThrow();
        CommentSectionEntity comment = new CommentSectionEntity(username, message, LocalDate.now(), event, user);
        commentSectionRepository.save(comment);
        return "redirect:/eventDetails/"+id;
    }
}


