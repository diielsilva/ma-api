package com.todev.ma.security;

import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.when;

@ExtendWith(value = MockitoExtension.class)
class UserDetailsRepositoryTest {
    @InjectMocks
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private IUserService userService;

    @Test
    void loadUserByUsername_MustReturnAnUser_WhenUsernameWasFound() {
        when(userService.findByEmail(anyString())).thenReturn(new User());

        assertDoesNotThrow(() -> userDetailsRepository.loadUserByUsername("bento_duarte@queirozgalvao.com"));
    }

    @Test
    void loadUserByUsername_MustNotReturnAnUser_WhenUsernameWasNotFound() {
        when(userService.findByEmail(anyString())).thenThrow(InvalidUserCredentialsException.class);

        assertThrows(InvalidUserCredentialsException.class, () -> userDetailsRepository.loadUserByUsername(""));
    }
}