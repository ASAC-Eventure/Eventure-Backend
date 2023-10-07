package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {


    AddEventJPARepository addEventJPARepository;


    public AdminController(AddEventJPARepository addEventJPARepository) {
        this.addEventJPARepository = addEventJPARepository;
    }

    @GetMapping("/adminHome")
    public String retrieveEventsRequests(Principal principal , Model model){
      List<AddEventEntity> newEvents = addEventJPARepository.findAll();
        model.addAttribute("events", newEvents);
        return "adminHome.html";}






}