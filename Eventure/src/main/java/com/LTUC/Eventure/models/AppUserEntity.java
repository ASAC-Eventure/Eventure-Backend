package com.LTUC.Eventure.models;

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
    private Long id;
    private String username;
    private String email;
    private String password;
    private String country;
    private String interests;
    private String image;
    private String dateOfBirth;
    @OneToMany(mappedBy = "user")
    private List<CommentSectionEntity> commentSection;

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
    public boolean isAccountNonExpired() {return true;}
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

}

