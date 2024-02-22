package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.UserS3Info;
import org.nhavronskyi.filebucketbackend.service.AwsS3Service;
import org.nhavronskyi.filebucketbackend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AwsS3Service service;

    @SneakyThrows
    @Override
    public UserS3Info getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(user -> new UserS3Info(service, user.getId()))
                .orElseThrow(() -> new Exception("User with this email doesn't exists"));
    }

    @Override
    public List<UserS3Info> getUserAllUsers() {
        return repository.findAll().stream()
                .map(user -> new UserS3Info(service, user.getId()))
                .toList();
    }
}
