package com.LTUC.Eventure.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class AppUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    private String username;
    private String password;
    private String country;
    private String interests;
    private String image;
    private LocalDate dateOfBirth;

        // constructors
    public AppUserEntity() {
    }

    public AppUserEntity(String username, String password, String country, String interests, String image, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.interests = interests;
        this.image = image;
        this.dateOfBirth = dateOfBirth;
    }
        // getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public String getInterests() {
        return interests;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

            //  setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
