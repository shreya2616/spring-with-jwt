package com.demo.authentication_service.service;

import com.demo.authentication_service.entity.Roles;
import com.demo.authentication_service.entity.UserCredentialsEntity;
import com.demo.authentication_service.repository.RoleRepository;
import com.demo.authentication_service.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCredentialsService {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserCredentialsRepository authDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserCredentialsEntity register(UserCredentialsEntity user) {
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Handle roles
        List<Roles> roles = new ArrayList<>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            for (Roles role : user.getRoles()) { // Assuming roles is now a List<Roles>
                Roles existingRole = roleRepository.findByRoleName(role.getRoleName())
                        .orElseGet(() -> {
                            // Create a new role if it doesn't exist
                            Roles newRole = new Roles();
                            newRole.setRoleName(role.getRoleName()); // Set the name of the new role
                            return roleRepository.save(newRole); // Save and return the new role
                        });
                roles.add(existingRole); // Add the found or newly created role to the list
            }
        }
        user.setRoles(roles); // Set the roles to the user

        // Save the user credentials with roles
        return authDao.saveAndFlush(user); // Save the user entity
    }

    public String generateToken(String name,String roles) {
        return jwtService.generateToken(name,roles);
    }

    public boolean verifyToken(String token) {
        jwtService.validateToken(token);
        return true;
    }
}
