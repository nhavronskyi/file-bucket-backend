package org.nhavronskyi.filebucketbackend.dao;

import org.nhavronskyi.filebucketbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
