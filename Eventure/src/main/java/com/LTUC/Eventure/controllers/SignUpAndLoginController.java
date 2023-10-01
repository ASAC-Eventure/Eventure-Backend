package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.entities.AppUserEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
    public RedirectView createUser(String username, String email, String password, String country, String interests, String dateOfBirth,String image, Model model){
        AppUserEntity existingUser = appUserJPARepository.findByUsername(username);
        if(existingUser!=null){
            model.addAttribute("errorMessage2", "User with this username already exists.");
            return new RedirectView("/signup");
        }else {
            AppUserEntity user = new AppUserEntity();

            user.setUsername(username);
            user.setEmail(email);
            user.setCountry(country);
            user.setInterests(String.join(", ", interests));
            user.setDateOfBirth(dateOfBirth);

            if(image==null|| image.isEmpty()){
                user.setImage("https://media.istockphoto.com/vectors/default-profile-picture-avatar-photo-placeholder-vector-illustration-vector-id1214428300?k=6&m=1214428300&s=170667a&w=0&h=hMQs-822xLWFz66z3Xfd8vPog333rNFHU6Q_kc9Sues=");
            }else{
                user.setImage(image);
            }

            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);

            appUserJPARepository.save(user);
            authWithHttpServletRequest(username,password);
        }
        return new RedirectView("/home");
    }
    public void authWithHttpServletRequest(String username, String password){
        try {
            request.login(username,password);
        }catch (ServletException e){
            e.printStackTrace();
        }
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
            return "redirect:/home";
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException: " + e.getMessage());
            redir.addFlashAttribute("errorMessage", "Invalid email or password");
            return "redirect:/login";
        }
    }
}
