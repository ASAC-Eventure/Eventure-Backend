package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.Enum.Roles;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.RoleJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;


@Controller
public class SignUpAndLoginController {
    private AppUserJPARepository appUserJPARepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleJPARepository roleJPARepository;

    @Autowired

    public SignUpAndLoginController(AppUserJPARepository appUserJPARepository) {
        this.appUserJPARepository = appUserJPARepository;
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signUpAndLogin";
    }

    @PostMapping("/signup")
    public RedirectView createUser(Model model, RedirectAttributes redir, String username, String email, String password, String confirmPassword, String country, String interests, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth) {
        AppUserEntity existingUser = appUserJPARepository.findByUsername(username);
        try {
            if (existingUser != null && !isPasswordValid(password) && !password.equals(confirmPassword)) {
                redir.addFlashAttribute("errorMessageUser", "Username already exists.");
                redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
                redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
            }
            if (existingUser != null && !isPasswordValid(password)) {
                redir.addFlashAttribute("errorMessageUser", "Username already exists.");
                redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
            }
            if (existingUser != null && !password.equals(confirmPassword)) {
                redir.addFlashAttribute("errorMessageUser", "Username already exists.");
                redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
            }
            if (!isPasswordValid(password) && !password.equals(confirmPassword)) {
                redir.addFlashAttribute("errorMessagePass", "Must be at least 8 characters and contain at least one uppercase letter.");
                redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
            }
            if (existingUser != null) {
                redir.addFlashAttribute("errorMessageUser", "Username already exists.");
                return new RedirectView("/signup");
            }
            if (!isPasswordValid(password)) {
                redir.addFlashAttribute("errorMessagePass", "Password must be at least 8 characters long and contain at least one uppercase letter.");
                return new RedirectView("/signup");
            }
            if (!password.equals(confirmPassword)) {
                redir.addFlashAttribute("errorMessageConfirmPass", "Passwords do not match.");
                return new RedirectView("/signup");
            } else {
                AppUserEntity user = new AppUserEntity();
                user.setUsername(username);
                user.setEmail(email);
                user.setCountry(country);
                user.setInterests(String.join(", ", interests));
                user.setDateOfBirth(dateOfBirth);
                user.setRoles(new RoleEntity(2L));
                String encryptedPassword = passwordEncoder.encode(password);
                user.setPassword(encryptedPassword);
                appUserJPARepository.save(user);
                authRegisterWithHttpServletRequest(username, password);

            }
            return new RedirectView("/signup");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessageSignup", "Signup failed due to an internal error.");
            return new RedirectView("/signup");
        }
    }

    //Sign-up authentication
    public void authRegisterWithHttpServletRequest(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
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
    public RedirectView login(RedirectAttributes redir, @RequestParam String username, @RequestParam String password) {

        try {
            authWithHttpServletRequest(username, password);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            AppUserEntity userEntity = appUserJPARepository.findByUsername(username);

            if (authentication != null) {
                RoleEntity role = userEntity.getRoles();
                if (role.getTitle() == Roles.USER) {
                    return new RedirectView("/");
                }


                if (role.getTitle() == Roles.ADMIN) {
                    return new RedirectView("/adminHome");
                }
            }

            return new RedirectView("/login");

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