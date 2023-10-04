package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class
ProfileController {

    @Autowired
    AppUserJPARepository appUserJPARepository;

    @GetMapping("/profile")
    public String myProfile(Principal principal , Model model) {
        if (principal != null) {
            String username = principal.getName();
            AppUserEntity appUserEntity = appUserJPARepository.findByUsername(username);
            model.addAttribute("userId",appUserEntity.getId());
            model.addAttribute("user", appUserEntity);
        }
        return "profile.html";
    }



    @GetMapping("/updatep")
    public String editProfilePage(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            AppUserEntity codeFellowUser = appUserJPARepository.findByUsername(username);
            m.addAttribute("user", codeFellowUser);
        }
        return "profile.html";
    }


    @PutMapping("/updatep/{id}")
    public RedirectView editMyProfile(@PathVariable Long id, @RequestParam String username, @RequestParam String dateOfBirth,
                                      @RequestParam String country, @RequestParam String interests, @RequestParam String image,
                                      Principal principal, RedirectAttributes redirectAttributes) {
        // Check if the logged-in user has the authority to update this profile
        AppUserEntity loggedInUser = appUserJPARepository.findByUsername(principal.getName());
        if (loggedInUser != null && loggedInUser.getId().equals(id)) {
            AppUserEntity appUser = appUserJPARepository.findById(id).orElseThrow();
            appUser.setUsername(username);
            appUser.setDateOfBirth(String.valueOf(LocalDate.parse(dateOfBirth))); // Parse string to LocalDate if needed
            appUser.setCountry(country);
            appUser.setImage(image);
            appUser.setInterests(interests);
            appUserJPARepository.save(appUser);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit another user's profile.");
        }
        return new RedirectView("/myProfile");
    }

//    @PostMapping("/updatep")
//    public String editMyProfile(
//            Principal p,
//            @RequestParam("username") String username,
//            @RequestParam("dateOfBirth") String dateOfBirth,
//            @RequestParam("country") String country,
//            @RequestParam("interests") String interests,
//            @RequestParam("image") String image,
//            @RequestParam("email") String email,
//            RedirectAttributes redirectAttributes) {
//
//        if (p != null && p.getName().equals(username)) {
//            AppUserEntity appUserEntity = appUserJPARepository.findByUsername(username);
//            appUserEntity.setUsername(username);
//            appUserEntity.setDateOfBirth(dateOfBirth);
//            appUserEntity.setCountry(country);
//            appUserEntity.setImage(image);
//            appUserEntity.setInterests(interests);
//            appUserEntity.setEmail(email);
//            appUserJPARepository.save(appUserEntity);
//            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit another user's profile page.");
//        }
//        return "redirect:/profile";
//    }
}
