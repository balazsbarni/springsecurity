package com.spring.securitydemo.service;

import com.spring.securitydemo.model.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //elvileg az adatbazisbol jon + roles majd
        AppUser appUser = loadAppUserByUsername(username);
        return new User(appUser.getUsername(), appUser.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    public AppUser loadAppUserByUsername(String username) {
        //elvileg itt jon a keres az adatbazistol
        return new AppUser("Batman", "password");
    }
}
