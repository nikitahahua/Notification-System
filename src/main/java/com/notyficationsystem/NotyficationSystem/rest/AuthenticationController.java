//package com.notyficationsystem.NotyficationSystem.rest;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthenticationController {
//
//    private final AuthenticationService authenticationService;
//
//    public AuthenticationController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/register")
//    @Operation(summary = "Register user")
//    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
//        return new ResponseEntity<>(authenticationService.register(registerRequest), HttpStatus.OK);
//    }
//
//    @GetMapping("/confirm-account")
//    @Operation(summary = "Confirm user account")
//    public ResponseEntity<Void> confirmUserAccount(@RequestParam("token") String token) {
//        authenticationService.confirmEmail(token);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    @Operation(summary = "Login user")
//    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
//        return new ResponseEntity<>(authenticationService.authenticate(loginRequest), HttpStatus.OK);
//    }
//
//}