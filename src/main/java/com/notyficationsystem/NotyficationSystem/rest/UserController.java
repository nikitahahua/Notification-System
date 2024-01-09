package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.ConfirmationTokenService;
import com.notyficationsystem.NotyficationSystem.service.JwtService;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import com.notyficationsystem.NotyficationSystem.service.impl.ConfirmationTokenServiceImpl;
import com.notyficationsystem.NotyficationSystem.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.readById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.readByEmail(email));
    }

    @GetMapping("/fullname/{fullname}")
    public ResponseEntity<User> getUserByFullName(@PathVariable String fullname) {
        return ResponseEntity.ok(userService.readByFullName(fullname));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id) {
        User existingUser = userService.readById(id);
        existingUser.setEmail(userService.readById(id).getEmail());
        existingUser.setFullname(userService.readById(id).getFullname());
        confirmationTokenService.sendConfirmationTokenToUser(existingUser);
        return ResponseEntity.ok(userService.update(existingUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}