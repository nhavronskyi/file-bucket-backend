package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.auth.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.auth.AuthResponse;

public interface AuthenticationService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}
