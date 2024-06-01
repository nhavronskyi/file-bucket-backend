package org.nhavronskyi.filebucketbackend.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.nhavronskyi.filebucketbackend.dao.UserRepository;
import org.nhavronskyi.filebucketbackend.entities.users.User;
import org.nhavronskyi.filebucketbackend.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserRepository userRepository;

    private static User creteUser(String email) {
        var user = new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        user.setPassword("test");
        return user;
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldAddUser() {
        var user = creteUser("test");
        userRepository.save(user);

        User actual = userRepository.findByEmail(user.getEmail()).orElse(null);
        User expected = new User(1L, user.getEmail(), user.getPassword(), user.getRole());

        assertEquals(expected, actual);
    }

    @Test
    void shouldAddOnlyUniqueUsers() {
        var list = List.of(creteUser("test"), creteUser("test"));

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.saveAll(list));
    }

    @Test
    void shouldGenerateIdsForUsers() {
        IntStream.range(0, 100)
                .mapToObj(i -> creteUser("test" + i))
                .forEach(userRepository::save);

        var users = userRepository.findAll()
                .stream()
                .map(User::getId)
                .distinct()
                .toList();

        assertEquals(100L, users.size());
    }
}


