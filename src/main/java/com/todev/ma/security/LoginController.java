package com.todev.ma.security;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/login")
class LoginController {
    private final LoginService loginService;

    LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @ResponseStatus(value = OK)
    LoginUserResponseData login(@RequestBody @Valid LoginUserRequestData data) {
        return loginService.login(data);
    }
}
