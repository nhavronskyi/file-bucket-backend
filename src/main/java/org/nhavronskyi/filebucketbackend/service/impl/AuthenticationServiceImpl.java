package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.AuthResponse;
import org.nhavronskyi.filebucketbackend.entities.User;
import org.nhavronskyi.filebucketbackend.enums.Role;
import org.nhavronskyi.filebucketbackend.service.AuthenticationService;
import org.nhavronskyi.filebucketbackend.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final AuthenticationManager manager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(AuthRequest request) {
        var user = User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.USER)
                .build();
        repository.save(user);

        return new AuthResponse(jwtService.generateToken(user));
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var token = repository.findByEmail(request.email())
                .map(jwtService::generateToken)
                .orElseThrow(() -> new UsernameNotFoundException("Something went wrong"));
        return new AuthResponse(token);
    }
}
