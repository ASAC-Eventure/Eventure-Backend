package com.LTUC.Eventure.models;

import com.LTUC.Eventure.Enum.Roles;
import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
import com.LTUC.Eventure.repositories.RoleJPARepository;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventure_users")
public class AppUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name",nullable = false)
    private String username;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "country",nullable = false)
    private String country;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Column(name = "interests")
    private String interests;
    @Column(name = "image")
    private String image;
    @Column(name = "date_Of_Birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name ="role_id")
    private RoleEntity roles ;

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
        RoleEntity role= getRoles();
        List<SimpleGrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getTitle().name()));
        return authorities;
    }

    //    @Override
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

