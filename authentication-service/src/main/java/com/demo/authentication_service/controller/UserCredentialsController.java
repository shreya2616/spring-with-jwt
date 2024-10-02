package com.demo.authentication_service.controller;

import com.demo.authentication_service.entity.Roles;
import com.demo.authentication_service.entity.UserCredentialsEntity;
import com.demo.authentication_service.service.JWTService;
import com.demo.authentication_service.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserCredentialsController {
    @Autowired
    JWTService jwtService;

    @Autowired
    private UserCredentialsService userCredService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserCredentialsEntity register(@RequestBody UserCredentialsEntity user) {
        return userCredService.register(user);
    }

    @GetMapping("/validate/token")
    public boolean validateToken(@RequestParam String token) {
        return userCredService.verifyToken(token);
    }

    @PostMapping("/validate/user")
    public String getToken(@RequestBody UserCredentialsEntity user) {
        System.out.println("user : " + user);

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
        );

        System.out.println("authenticated?? : " + authenticate.isAuthenticated());

        if (authenticate.isAuthenticated()) {
            // Convert List<Roles> to List<String> (assuming the 'Roles' class has a 'getName()' method)
            List<String> roleNames = user.getRoles().stream()
                    .map(Roles::getRoleName) // Adjust this if your getter is different
                    .collect(Collectors.toList());

            return userCredService.generateToken(user.getName(), String.valueOf(roleNames));
        }

        return null;
    }

}

