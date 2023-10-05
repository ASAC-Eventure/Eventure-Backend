package com.LTUC.Eventure.bo;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserRegistrationDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z]).{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter")
    private String password;

    @NotEmpty(message = "Country is required")
    private String country;

    private List<String> interests;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of Birth is required")
    private LocalDate dateOfBirth;
}