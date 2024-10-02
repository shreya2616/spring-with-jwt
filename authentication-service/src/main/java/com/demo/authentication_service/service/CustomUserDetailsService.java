package com.demo.authentication_service.service;

import com.demo.authentication_service.entity.UserCredentialsEntity;
import com.demo.authentication_service.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentialsEntity> authUser = userCredentialsRepository.findByName(username);

        return authUser.map((user)-> new CustomUserDetails(user.getName(),user.getPassword(),user.getRoles()))
                .orElseThrow(()->new UsernameNotFoundException(username + " not found"));

    }
}
