package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
