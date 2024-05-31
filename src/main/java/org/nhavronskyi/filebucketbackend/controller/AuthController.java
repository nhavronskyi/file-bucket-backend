package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.auth.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.auth.AuthResponse;
import org.nhavronskyi.filebucketbackend.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authenticationService.login(request);
    }
}
