package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.users.UserS3Info;
import org.nhavronskyi.filebucketbackend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("user")
    public UserS3Info getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("users")
    public List<UserS3Info> getAllUsers() {
        return userService.getUserAllUsers();
    }
}
