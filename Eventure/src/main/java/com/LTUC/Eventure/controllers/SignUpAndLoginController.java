package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.Enum.Roles;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.RoleJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

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

    @Autowired
    private RoleJPARepository roleJPARepository;


    @GetMapping("/signup")
    public String signUpPage(Model model){return "signUpAndLogin";}
    @PostMapping("/signup")
    public RedirectView createUser(String username, String email, String password, String country, String interests, LocalDate dateOfBirth, String image, Model model){
        try {
            AppUserEntity existingUser = appUserJPARepository.findByUsername(username);
            if (existingUser != null) {
                model.addAttribute("errorMessage", "User with this username already exists.");
                return new RedirectView("/signup");
            }

            AppUserEntity user = new AppUserEntity();
            user.setUsername(username);
            user.setEmail(email);
            user.setCountry(country);
            user.setInterests(String.join(", ", interests));
            user.setDateOfBirth(dateOfBirth);

            if (image == null || image.isEmpty()) {
                user.setImage("https://media.istockphoto.com/vectors/default-profile-picture-avatar-photo-placeholder-vector-illustration-vector-id1214428300?k=6&m=1214428300&s=170667a&w=0&h=hMQs-822xLWFz66z3Xfd8vPog333rNFHU6Q_kc9Sues=");
            } else {
                user.setImage(image);
            }
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            appUserJPARepository.save(user);

            authWithHttpServletRequest(username, password);
//            userService.saveUserWithDefaultRole(user);
            return new RedirectView("/home");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Signup failed due to an internal error.");
            return new RedirectView("/signup");
        } }
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
    public RedirectView login(RedirectAttributes redir, @RequestParam String username, @RequestParam String password) {
        try {
            AppUserEntity userEntity = appUserJPARepository.findByUsername(username);

            if (userEntity == null) {
                redir.addFlashAttribute("errorMessage", "User not found");
                return new RedirectView("/login");
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            if (isAdmin(userEntity)) {
                return new RedirectView("/adminHome");
            }

            return new RedirectView("/home");
        } catch (AuthenticationException e) {
            System.out.println("AuthenticationException: " + e.getMessage());
            redir.addFlashAttribute("errorMessage", "Invalid email or password");
            return new RedirectView("/login");
        }
    }

    private boolean isAdmin(AppUserEntity userEntity) {
        Optional<RoleEntity> adminRole = roleJPARepository.findRoleEntityByTitle(Roles.ADMIN.name());
        return userEntity.getRoles().equals(adminRole.orElse(null));
    }


}
