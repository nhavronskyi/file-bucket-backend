package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.AuthResponse;
import org.nhavronskyi.filebucketbackend.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authenticationService.register(request);
    }

    @GetMapping("login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authenticationService.login(request);
    }
}
