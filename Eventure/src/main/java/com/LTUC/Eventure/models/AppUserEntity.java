package com.LTUC.Eventure.models;

<<<<<<< HEAD
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class AppUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
=======
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
>>>>>>> origin/reneh-comment
    private Long id;
    private String username;
    private String email;
    private String password;
    private String country;
    private String interests;
    private String image;
    private String dateOfBirth;
<<<<<<< HEAD

        // constructors
    public AppUserEntity() {
    }
=======
    @OneToMany(mappedBy = "user")
    private List<CommentSectionEntity> commentSection;
>>>>>>> origin/reneh-comment

    public AppUserEntity(String username, String email, String password, String country, String image, String interests, String dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
        this.image=image;
        this.interests = interests;
        this.dateOfBirth = dateOfBirth;
    }
    @Override
<<<<<<< HEAD
    public boolean isAccountNonExpired() {
        return true;
    }
=======
    public boolean isAccountNonExpired() {return true;}
>>>>>>> origin/reneh-comment
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
<<<<<<< HEAD
        // getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

            //  setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setImage(String image) {
        this.image = image;
    }
=======

>>>>>>> origin/reneh-comment
}

