package org.nhavronskyi.filebucketbackend.service;

import org.nhavronskyi.filebucketbackend.entities.User;
import org.nhavronskyi.filebucketbackend.entities.UserS3Info;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface UserService {
    static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    UserS3Info getUserByEmail(String email);

    List<UserS3Info> getUserAllUsers();
}
