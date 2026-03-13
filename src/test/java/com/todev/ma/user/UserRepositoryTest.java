package com.todev.ma.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(value = {TestcontainersConfiguration.class})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail_MustReturnAnUser_WhenEmailWasFound() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        userRepository.save(user);

        Optional<User> retrieved = userRepository.findByEmail(user.getEmail());

        assertTrue(retrieved.isPresent());
    }

    @Test
    void findByEmail_MustNotReturnAnUser_WhenEmailWasNotFound() {
        Optional<User> retrieved = userRepository.findByEmail("martin-aparicio96@sp.senac.com.br");

        assertTrue(retrieved.isEmpty());
    }

    @Test
    void findByCpf_MustReturnAnUser_WhenCpfWasFound() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        userRepository.save(user);

        Optional<User> retrieved = userRepository.findByCpf(user.getCpf());

        assertTrue(retrieved.isPresent());
    }

    @Test
    void findByCpf_MustNotReturnAnUser_WhenCpfWasNotFound() {
        Optional<User> retrieved = userRepository.findByCpf("54463972906");

        assertTrue(retrieved.isEmpty());
    }

    @Test
    void findByPhone_MustReturnAnUser_WhenPhoneWasFound() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        userRepository.save(user);

        Optional<User> retrieved = userRepository.findByPhone(user.getPhone());

        assertTrue(retrieved.isPresent());
    }

    @Test
    void findByPhone_MustNotReturnAnUser_WhenPhoneWasNotFound() {
        Optional<User> retrieved = userRepository.findByPhone("27991911428");

        assertTrue(retrieved.isEmpty());
    }
}