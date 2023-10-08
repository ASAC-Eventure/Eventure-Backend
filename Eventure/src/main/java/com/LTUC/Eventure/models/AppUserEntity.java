package com.LTUC.Eventure.models;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;


@Entity
@Data
@NoArgsConstructor
@Table(name = "eventure_users")
public class AppUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "interests")
    private String interests;

    @Column(name = "date_Of_Birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> bookedEvents;

    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roles;

    @OneToMany(mappedBy = "user")
    @Column(name = "events_approved_byAdmin_booked_byUser" )
    List<AddEventEntity> newEvents;
    public AppUserEntity(String username, String email, String password, String country, String interests, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.country = country;
        this.interests = interests;
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


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
        RoleEntity role = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (role != null && role.getTitle() != null) {
            authorities.add(new SimpleGrantedAuthority(role.getTitle().name()));
        }

        return authorities;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        RoleEntity role = getRoles();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role.getTitle().name()));
//        return authorities;
//    }

//        @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        RoleEntity role = getRoles();
//        if (role != null) {
//            return Collections.singletonList(new SimpleGrantedAuthority(role.getTitle().name()));
//        }
//        return Collections.emptyList();
//    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}

