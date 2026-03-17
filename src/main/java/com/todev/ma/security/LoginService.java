package com.todev.ma.security;

import com.todev.ma.shared.Trackable;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class LoginService extends Trackable {
    private final TokenService tokenService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    LoginService(TokenService tokenService, IUserService userService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    LoginUserResponseData login(LoginUserRequestData data) {
        try {
            User user = userService.findByEmail(data.email());
            boolean arePasswordsDifferent = !passwordEncoder.matches(data.password(), user.getPassword());

            if (arePasswordsDifferent) {
                throw new InvalidUserCredentialsException();
            }

            String token = tokenService.create(user.getEmail());

            return new LoginUserResponseData(
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getPhone(),
                    user.getGender(),
                    user.getRole(),
                    user.getBirthDate(),
                    user.getDisabilities(),
                    token
            );
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new InvalidUserCredentialsException();
        }
    }
}
