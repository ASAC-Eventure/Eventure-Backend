package com.LTUC.Eventure.controllers;
import com.LTUC.Eventure.entities.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GeneralControllers {
    @Autowired
    AppUserJPARepository appUserJPARepository;

        @GetMapping("/")
        public String Home(){return "index";}

    @GetMapping("/home")
    public String securedHome(){return "indexAfter";}
    @GetMapping("/aboutUs")
    public String aboutUs(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(username.equals("anonymousUser")){
            model.addAttribute("isUsernameFound", "no");
        }else {
            model.addAttribute("isUsernameFound", "yes");
        }
        return "aboutUs.html";
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
