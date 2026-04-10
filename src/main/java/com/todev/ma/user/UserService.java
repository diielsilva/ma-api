package com.todev.ma.user;

import com.todev.ma.shared.ModelNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class UserService implements IUserService {
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

    @Transactional
    void update(Authentication session, UpdateUserRequestData data) {
        User user = findByEmail(session.getName());

        boolean wasEmailChanged = !user.getEmail().equals(data.email());

        if (wasEmailChanged && isEmailRegistered(data.email())) {
            throw new DuplicatedEmailException();
        }

        boolean wasCpfChanged = !user.getCpf().equals(data.cpf());

        if (wasCpfChanged && isCpfRegistered(data.cpf())) {
            throw new DuplicatedCpfException();
        }

        boolean wasPhoneChanged = !user.getPhone().equals(data.phone());

        if (wasPhoneChanged && isPhoneRegistered(data.phone())) {
            throw new DuplicatedPhoneException();
        }

        if (data.password() != null) {
            String encodedPassword = passwordEncoder.encode(data.password());

            user.setPassword(encodedPassword);
        }

        user.setName(data.name());
        user.setEmail(data.email());
        user.setCpf(data.cpf());
        user.setPhone(data.phone());
        user.setGender(Gender.valueOf(data.gender().toUpperCase()));
        user.setBirthDate(data.birthDate());
        user.setDisabilities(data.disabilities());

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email)  {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByCpf(String cpf)  {
        return userRepository.findByCpf(cpf).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getSessionOwner(Authentication session)  {
        return findByEmail(session.getName());
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
