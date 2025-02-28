package com.daytona.cruddemo.service;

import com.daytona.cruddemo.entity.Role;
import com.daytona.cruddemo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name()) // Convert ENUM to String role
                .disabled(!user.isEnabled()) // Disable account if `enabled` is false
                .build();
    }

//    public void saveUser(String username, String Password, Role role) {
//        String encodedPassword = passwordEncoder.encode(Password);
//        Users user = new Users(username, encodedPassword, role, true); // Enabled by default
//        repo.save(user); // ID is auto-generated
//    }

    public void saveUser(String username, String password, Role role) {
        Users user = new Users(username, passwordEncoder.encode(password), role, true);
        repo.save(user);
    }

    public void disableUser(String username) {
        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        repo.save(user);
    }

    public void enableUser(String username) {
        Users user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        repo.save(user);
    }
}
