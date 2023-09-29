package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUser;
import com.LTUC.Eventure.repositories.AppUserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class GeneralControllers {

    @GetMapping("/")
    public String showIndex(){
            return "index";
        }

    @GetMapping("/aboutUs")
    public String aboutUs(){return "aboutUs.html";}
}

