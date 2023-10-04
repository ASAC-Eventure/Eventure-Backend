package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.config.UserDetailsServiceImpl;
import com.LTUC.Eventure.models.AdminUserEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AdminJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminJPARepository adminUserJPARepostory;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    HttpServletRequest request;
    @Autowired

    @GetMapping("/signupAsAdmin")
    public String retrieveAdminSignup(){ return "signupAndLoginAdmin.html";}
    @PostMapping("/signupAsAdmin")
    public RedirectView signupAsAdmin(String username, String password,String email,String dateOfBirth, Model m){

        AdminUserEntity signedupUser = adminUserJPARepostory.findByUsername(username);
        if(signedupUser != null){
            m.addAttribute("errorMessage", "Username exits.");
        }
        String HashedPassword= passwordEncoder.encode(password);
        AdminUserEntity newAdminUser = new AdminUserEntity(username,HashedPassword,email,dateOfBirth);
        adminUserJPARepostory.save(newAdminUser);
        authWithHttpServletRequest(username,password);
        return new RedirectView("/");
    }
//    @GetMapping("/adminHome")
//    public String retrieveAdminHome(){ return "hi admin home";}
    public void authWithHttpServletRequest(String username,String password){
        try {
            request.login(username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @GetMapping("/loginAsAdmin")
    public String retrieveAdminLogin(){ return "signupAndLoginAdmin.html";}
    @PostMapping("/loginAsAdmin")
    public RedirectView loginAsAdmin(String username,String password){
        return new RedirectView("/adminHome");
    }


//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//        List<AppUserEntity> users = userDetailsService.getAllUsers();
//        model.addAttribute("users", users);
//        return "admin/dashboard";
//    }
//
//    @PostMapping("/deleteTicket")
//    public String deleteTicket(@RequestParam("ticketId") Long ticketId) {
//        ticketService.deleteTicket(ticketId);
//        return "redirect:/admin/dashboard";
//    }


}