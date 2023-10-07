//package com.LTUC.Eventure.controllers;
//
//import com.LTUC.Eventure.Enum.Roles;
//import com.LTUC.Eventure.bo.CreateUserRequest;
//import com.LTUC.Eventure.models.AppUserEntity;
//import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
//import com.LTUC.Eventure.repositories.AppUserJPARepository;
//import com.LTUC.Eventure.repositories.RoleJPARepository;
//import com.LTUC.Eventure.services.user.AppUserServiceImpl;
//import com.LTUC.Eventure.util.exception.BodyGuardException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.view.RedirectView;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//
//@Controller
//public class RegestrationController {
//    @Autowired
//    AppUserJPARepository appUserJPARepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    private HttpServletRequest request;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private RoleJPARepository roleJPARepository;
//
//    @Autowired
//    private AppUserServiceImpl appUserService;
//
//
//    @GetMapping("/signup")
//    public String signUpPage() {
//        return "signUpAndLogin";
//    }
//
//    @PostMapping("/signup")
//    public RedirectView createUser (Model model, RedirectAttributes redir, String confirmPassword,
//                                    @ModelAttribute CreateUserRequest userDTO,
//                                    BindingResult result){
//
//        appUserService.createPreValidation(userDTO);
//        try {
//            String username = userDTO.getUsername();
//            String email = userDTO.getEmail();
//            String password = userDTO.getPassword();
//            String country = userDTO.getCountry();
//            List<String> interests = userDTO.getInterests();
//            LocalDate dateOfBirth = userDTO.getDateOfBirth();
//
//            AppUserEntity existingUser = appUserJPARepository.findByUsername(username);
//            if (existingUser != null) {
//                redir.addFlashAttribute("errorMessageUser", "Username already exists.");
//                return new RedirectView("/signup");
//            }
//
//            if (!password.equals(confirmPassword)) {
//                redir.addFlashAttribute("errorMessageConfirmPass", "Password and Confirm Password do not match");
//                return new RedirectView("/signup");
//            }
//
//            if (result.hasErrors()) {
//                redir.addFlashAttribute("errorMessagePass", "Password must be at least 8 characters long and contain at least one uppercase letter.");
//                return new RedirectView("/signup");
//            }
//
//            AppUserEntity user = new AppUserEntity();
//            user.setUsername(username);
//            user.setEmail(email);
//            user.setCountry(country);
//            user.setInterests(String.join(", ", interests));
//            user.setDateOfBirth(dateOfBirth);
//            user.setRoles(new RoleEntity(2L));
//            String encryptedPassword = passwordEncoder.encode(password);
//            user.setPassword(encryptedPassword);
//            appUserJPARepository.save(user);
//            authRegisterWithHttpServletRequest(username, password);
//
//            return new RedirectView("/signup");
//        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("errorMessageSignup", "Signup failed due to an internal error.");
//            return new RedirectView("/signup");
//        }
//    }
//    public void authRegisterWithHttpServletRequest(String username, String password) {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//
//    public void authWithHttpServletRequest(String username, String password){
//        try {
//            request.login(username,password);
//        }catch (ServletException e){
//            e.printStackTrace();
//        }
//    }
//
//
//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        return "signUpAndLogin";
//    }
//
//    @PostMapping("/login")
//    public RedirectView login(RedirectAttributes redir, @RequestParam String username, @RequestParam String password) {
//
//        try {
//            authWithHttpServletRequest(username, password);
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            AppUserEntity userEntity = appUserJPARepository.findByUsername(username);
//
//            if (authentication != null) {
//                RoleEntity role = userEntity.getRoles();
//                if (role.getTitle() == Roles.USER) {
//                    return new RedirectView("/");
//                }
//
//
//                if (role.getTitle() == Roles.ADMIN) {
//                    return new RedirectView("/adminHome");
//                }
//            }
//
//            return new RedirectView("/login");
//
//        } catch (AuthenticationException e) {
//            System.out.println("AuthenticationException: " + e.getMessage());
//            redir.addFlashAttribute("errorMessage", "Invalid email or password");
//            return new RedirectView("/login");
//        }
//    }
//
//    private boolean isAdmin(AppUserEntity userEntity) {
//        Optional<RoleEntity> adminRole = roleJPARepository.findRoleEntityByTitle(Roles.ADMIN.name());
//        return userEntity.getRoles().equals(adminRole.orElse(null));
//    }
//
//    @GetMapping("/showForm")
//    public String showForm(Model model) {
//        model.addAttribute("createUserRequest", new CreateUserRequest());
//        return "form-page";
//    }
//
//    @PostMapping("/submit")
//    public String submitForm(@ModelAttribute("createUserRequest") CreateUserRequest createUserRequest, BindingResult bindingResult) {
//
//        try {
//
//            appUserService.createPreValidation(createUserRequest);
//        } catch (BodyGuardException e) {
//            String[] errorMessages = e.getMessage().split(",");
//            for (String errorMessage : errorMessages) {
//                bindingResult.rejectValue("username", "error.code", errorMessage);
//            }
//        }
//
//        if (bindingResult.hasErrors()) {
//
//            return "form-page";
//        }
//
//        return "signUpAndLogin";
//    }
//}
//
