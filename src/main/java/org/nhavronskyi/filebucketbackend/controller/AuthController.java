package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.RegisterRequest;
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
    public void register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
    }
}
