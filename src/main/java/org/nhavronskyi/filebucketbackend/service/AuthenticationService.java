package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.AuthRequest;
import org.nhavronskyi.filebucketbackend.entities.AuthResponse;

public interface AuthenticationService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}
