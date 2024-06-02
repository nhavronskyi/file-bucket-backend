package org.nhavronskyi.filebucketbackend.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.auth.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.auth.AuthResponse;
import org.nhavronskyi.filebucketbackend.entities.users.User;
import org.nhavronskyi.filebucketbackend.enums.Role;
import org.nhavronskyi.filebucketbackend.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrationShouldCreateUser() {
        AuthRequest authRequest = new AuthRequest("test@example.com", "testPassword");

        when(passwordEncoder.encode(authRequest.password())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthResponse authResponse = authenticationService.register(authRequest);

        verify(userRepository, times(1)).save(any(User.class));
        assertNotNull(authResponse);
        assertEquals("jwtToken", authResponse.token());
    }

    @Test
    void loginShouldAuthenticateUserAndReturnToken() {
        AuthRequest authRequest = new AuthRequest("test@example.com", "testPassword");

        User user = User.builder()
                .email(authRequest.email())
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(authRequest.email())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        AuthResponse authResponse = authenticationService.login(authRequest);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertNotNull(authResponse);
        assertEquals("jwtToken", authResponse.token());
    }

    @Test
    void loginShouldThrowExceptionWhenUserNotFound() {
        AuthRequest authRequest = new AuthRequest("nonexistent@example.com", "testPassword");

        when(userRepository.findByEmail(authRequest.email())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(authRequest));
    }
}
