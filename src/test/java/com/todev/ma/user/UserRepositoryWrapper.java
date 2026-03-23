package com.todev.ma.user;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryWrapper {
    private final UserRepository userRepository;

    UserRepositoryWrapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
