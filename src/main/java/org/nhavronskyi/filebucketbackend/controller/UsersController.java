package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.UserS3Info;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class UsersController {
    private final UserRepository repository;
    private final AwsS3Service service;

    @GetMapping("user")
    public UserS3Info getUser(@RequestParam String email) {
        return repository.findByEmail(email)
                .map(user -> new UserS3Info(service, user.getId()))
                .orElse(null);
    }

    @GetMapping("users")
    public List<UserS3Info> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> new UserS3Info(service, user.getId()))
                .toList();
    }
}
