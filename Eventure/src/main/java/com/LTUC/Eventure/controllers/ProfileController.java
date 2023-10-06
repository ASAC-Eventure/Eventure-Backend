package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.config.UserDetailsServiceImpl;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class
ProfileController {

    @Autowired
    AppUserJPARepository appUserJPARepository;

    @GetMapping("/profile")
    public String myProfile(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            AppUserEntity appUserEntity = appUserJPARepository.findByUsername(username);
            model.addAttribute("user", appUserEntity);
        }
        return "profile.html";
    }

    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AppUserEntity user = appUserJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "editProfile";
    }


    @PutMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") AppUserEntity updatedUser, RedirectAttributes redir) {
        AppUserEntity user = appUserJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        System.out.println("Received dateOfBirth: " + updatedUser.getDateOfBirth());

//        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setCountry(updatedUser.getCountry());
        user.setInterests(updatedUser.getInterests());
        user.setDateOfBirth(updatedUser.getDateOfBirth());

        appUserJPARepository.save(user);
        redir.addFlashAttribute("successMessage", "Updated Successfully!");

        System.out.println("User ID: " + id);
        System.out.println("Redirecting to /profile");


        return "redirect:/edit/" + id;
    }

}
