package com.todev.ma.security;

import com.todev.ma.user.IUserService;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
class UserDetailsRepository implements UserDetailsService {
    private final IUserService userService;

    UserDetailsRepository(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public @NullMarked UserDetails loadUserByUsername(String email) {
        return userService.findByEmail(email);
    }
}
