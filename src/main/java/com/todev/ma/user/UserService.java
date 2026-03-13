package com.todev.ma.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    void create(CreateUserRequestData data) {
        if (isEmailRegistered(data.email())) {
            throw new DuplicatedEmailException();
        }

        if (isCpfRegistered(data.cpf())) {
            throw new DuplicatedCpfException();
        }

        if (isPhoneRegistered(data.phone())) {
            throw new DuplicatedPhoneException();
        }

        String encodedPassword = passwordEncoder.encode(data.password());

        User user = new User(
                data.name(),
                data.email(),
                encodedPassword,
                data.cpf(),
                data.phone(),
                Gender.valueOf(data.gender().toUpperCase()),
                Role.USER,
                data.birthDate(),
                data.disabilities()
        );

        userRepository.save(user);
    }

    private boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isCpfRegistered(String cpf) {
        return userRepository.findByCpf(cpf).isPresent();
    }

    private boolean isPhoneRegistered(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }
}
