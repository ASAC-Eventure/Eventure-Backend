package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.security.Principal;

@Controller
public class ProfileController {


    private AppUserJPARepository appUserJPARepository;

    @Autowired
    public ProfileController(AppUserJPARepository appUserJPARepository) {
        this.appUserJPARepository = appUserJPARepository;
    }

    @GetMapping("/profile")
    public String myProfile(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            AppUserEntity appUserEntity = appUserJPARepository.findByUsername(username);
            model.addAttribute("user", appUserEntity);
        }
        return "profile.html";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AppUserEntity user = appUserJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "editProfile";
    }

    @PutMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") AppUserEntity updatedUser) {
        AppUserEntity user = appUserJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

//        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setCountry(updatedUser.getCountry());
        String interests = String.join(", ", updatedUser.getInterests());
        user.setInterests(interests);
        user.setDateOfBirth(updatedUser.getDateOfBirth());

        appUserJPARepository.save(user);
        return "redirect:/profile";
    }


}