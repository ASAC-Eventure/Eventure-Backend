package com.LTUC.Eventure.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralControllers {

        @GetMapping("/")
        public String showIndex(){
            return "index";
        }
    }

