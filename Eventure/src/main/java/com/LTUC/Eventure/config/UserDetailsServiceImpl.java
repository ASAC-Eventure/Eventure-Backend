package com.LTUC.Eventure.config;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AppUserJPARepository appUserJPARepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserEntity user= appUserJPARepository.findByUsername(username);
        if(user==null){
            System.out.println("User Not Found" + username);
            throw new UsernameNotFoundException("User" + username + "Was Not Found");
        }
        System.out.println("Found User: " +user.getUsername());
        return user;
    }


}
