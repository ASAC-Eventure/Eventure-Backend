package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@Controller
public class SignUpAndLoginController {
    @Autowired
    AppUserJPARepository appUserJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/signup")
    public String signUpPage(Model model){return "signUpAndLogin";}
    @PostMapping("/signup")
    public RedirectView createUser(RedirectAttributes redir,String username, String email, String password,String confirmPassword, String country, String interests, String dateOfBirth){
        AppUserEntity existingUser = appUserJPARepository.findByUsername(username);

        if (existingUser!=null && !isPasswordValid(password) && !password.equals(confirmPassword)) {
            redir.addFlashAttribute("errorMessageUser", "Username already exists.");
            redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
            redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
        }
        if(existingUser!=null && !isPasswordValid(password)){
            redir.addFlashAttribute("errorMessageUser", "Username already exists.");
            redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
        }
        if(existingUser!=null && !password.equals(confirmPassword)){
            redir.addFlashAttribute("errorMessageUser", "Username already exists.");
            redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
        }
        if(!isPasswordValid(password) && !password.equals(confirmPassword)){
            redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
            redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
        }
        if(existingUser!=null){
            redir.addFlashAttribute("errorMessageUser", "Username already exists.");
            return new RedirectView("/signup");
        }if (!isPasswordValid(password)) {
            redir.addFlashAttribute("errorMessagePass", "Password must be at least 8 characters long and contain at least one uppercase letter.");
            return new RedirectView("/signup");
        } if (!password.equals(confirmPassword)) {
            redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
            return new RedirectView("/signup");
        }else {
            AppUserEntity user = new AppUserEntity();

            user.setUsername(username);
            user.setEmail(email);
            user.setCountry(country);
            user.setInterests(String.join(", ", interests));
            user.setDateOfBirth(dateOfBirth);


            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);

            appUserJPARepository.save(user);
            authWithHttpServletRequest(username,password);
        }
        return new RedirectView("/");
    }
    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username,password);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }

    //========= password validation method
    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*");
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "signUpAndLogin";
    }

    @PostMapping("/login")
    public String login(RedirectAttributes redir, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException: " + e.getMessage());
            redir.addFlashAttribute("errorMessage", "Invalid email or password");
            return "redirect:/login";
        }
    }
}
