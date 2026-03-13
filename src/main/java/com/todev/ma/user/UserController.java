package com.todev.ma.user;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

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
}
