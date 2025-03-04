package com.daytona.cruddemo.rest;

import com.daytona.cruddemo.entity.Users;
import com.daytona.cruddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        userService.saveUser(user.getUsername(), user.getPassword(), user.getRole());
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }

    @PutMapping("/disable/{username}")
    public ResponseEntity<String> disableUser(@PathVariable String username) {
        userService.disableUser(username);
        return ResponseEntity.ok("User disabled successfully!");
    }

    @PutMapping("/enable/{username}")
    public ResponseEntity<String> enableUser(@PathVariable String username) {
        userService.enableUser(username);
        return ResponseEntity.ok("User enabled successfully!");
    }

}
