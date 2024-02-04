package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.RegisterRequest;
import org.nhavronskyi.filebucketbackend.entities.User;
import org.nhavronskyi.filebucketbackend.enums.Role;
import org.nhavronskyi.filebucketbackend.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    @Override
    public void register(RegisterRequest request) {
        var user = User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(Role.USER)
                .build();
        repository.save(user);
    }
}
