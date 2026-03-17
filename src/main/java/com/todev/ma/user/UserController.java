package com.todev.ma.user;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/users")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(value = CREATED)
    void create(@RequestBody @Valid CreateUserRequestData data) {
        userService.create(data);
    }

    @PatchMapping
    @ResponseStatus(value = OK)
    void update(Authentication session, @RequestBody @Valid UpdateUserRequestData data) {
        userService.update(session, data);
    }
}
