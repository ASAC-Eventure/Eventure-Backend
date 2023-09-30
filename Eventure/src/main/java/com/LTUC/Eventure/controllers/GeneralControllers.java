package com.LTUC.Eventure.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GeneralControllers {

        @GetMapping("/")
        public String Home(){
            return "index";
        }

    @GetMapping("/home")
    public String securedHome(){
        return "indexAfter";
    }


    @GetMapping("/terms-conditions")
         public String termsAndCondintions(){
         return "T&C.html";
        }

        @GetMapping("/logout")
        public RedirectView logOut(){
            return new RedirectView("/");
        }
}
