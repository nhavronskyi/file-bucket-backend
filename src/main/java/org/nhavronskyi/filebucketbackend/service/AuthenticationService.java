package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest request);
}
