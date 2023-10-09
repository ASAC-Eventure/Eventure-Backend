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
public class
ProfileController {

    @Autowired
    AppUserJPARepository appUserJPARepository;

    @GetMapping("/profile")
    public String myProfile(Principal principal , Model model) {
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

//    @GetMapping("/updatep")
//    public String editProfilePage(Principal p, Model m) {
//        if (p != null) {
//            String username = p.getName();
//            AppUserEntity codeFellowUser = appUserJPARepository.findByUsername(username);
//            m.addAttribute("user", codeFellowUser);
//        }
//        return "profile.html";
//    }
//    @PutMapping("/updateProfile")
//    public RedirectView editMyProfile (Principal p, String username, LocalDate dateOfBirth, String country, String interests, String image, RedirectAttributes redirectAttributes){
//        if ((p != null) && (p.getName().equals(username))) {
//            AppUser appUser=appUserJPA.findByUsername(username);
//            appUser.setUsername(username);
//            appUser.setDateOfBirth(dateOfBirth);
//            appUser.setCountry(country);
//            appUser.setImage(image);
//            appUser.setInterests(interests);
//            appUserJPA.save(appUser);
//        }else{
//            redirectAttributes.addFlashAttribute("errorMessage","Cannot edit another user profile page");
//        }
//        return new RedirectView("/myProfile");
//    }


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
////            appUserEntity.setImage(image);
//            appUserEntity.setInterests(interests);
//            appUserEntity.setEmail(email);
//            appUserJPARepository.save(appUserEntity);
//            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit another user's profile page.");
//        }
//        return "redirect:/profile";
//    }


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
