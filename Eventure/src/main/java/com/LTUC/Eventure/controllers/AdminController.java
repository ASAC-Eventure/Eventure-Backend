package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController {

    @Autowired
    AppUserJPARepository appUserJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    HttpServletRequest request;
    @ResponseBody
    @GetMapping("/adminHome")
    public String retrieveAdminHome(){ return "hi admin home";}
    public void authWithHttpServletRequest(String adminUsername,String adminPassword){
        try {
            request.login(adminUsername,adminPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}