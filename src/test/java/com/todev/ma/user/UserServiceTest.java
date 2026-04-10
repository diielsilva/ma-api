package com.todev.ma.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.when;

@ExtendWith(value = MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void create_MustCreateAnUser_WhenNoExceptionIsThrown() {
        CreateUserRequestData data = new CreateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                LocalDate.of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(empty());
        when(userRepository.findByCpf(anyString())).thenReturn(empty());
        when(userRepository.findByPhone(anyString())).thenReturn(empty());

        assertDoesNotThrow(() -> userService.create(data));
    }

    @Test
    void create_MustNotCreateAnUser_WhenEmailAlreadyExists() {
        CreateUserRequestData data = new CreateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(DuplicatedEmailException.class, () -> userService.create(data));
    }

    @Test
    void create_MustNotCreateAnUser_WhenCpfAlreadyExists() {
        CreateUserRequestData data = new CreateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(empty());
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(DuplicatedCpfException.class, () -> userService.create(data));
    }

    @Test
    void create_MustNotCreateAnUser_WhenPhoneAlreadyExists() {
        CreateUserRequestData data = new CreateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(empty());
        when(userRepository.findByCpf(anyString())).thenReturn(empty());
        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(DuplicatedPhoneException.class, () -> userService.create(data));
    }

    @Test
    void update_MustUpdateAnUser_WhenNoExceptionIsThrownAndDataDidNotChange() {
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

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        UpdateUserRequestData data = new UpdateUserRequestData(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                "MALE",
                of(1972, 8, 2),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.update(session, data));
    }

    @Test
    void update_MustUpdateAnUser_WhenNoExceptionIsThrownAndDataHasChanged() {
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

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        UpdateUserRequestData data = new UpdateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user)).thenReturn(empty());

        when(userRepository.findByCpf(anyString())).thenReturn(empty());

        when(userRepository.findByPhone(anyString())).thenReturn(empty());

        assertDoesNotThrow(() -> userService.update(session, data));
    }

    @Test
    void update_MustNotUpdateAnUser_WhenNewEmailAlreadyExists() {
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

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        UpdateUserRequestData data = new UpdateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user)).thenReturn(Optional.of(user));

        assertThrows(DuplicatedEmailException.class, () -> userService.update(session, data));
    }

    @Test
    void update_MustNotUpdateAnUser_WhenNewCpfAlreadyExists() {
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

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        UpdateUserRequestData data = new UpdateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user)).thenReturn(empty());

        when(userRepository.findByCpf(anyString())).thenReturn(Optional.of(user));

        assertThrows(DuplicatedCpfException.class, () -> userService.update(session, data));
    }

    @Test
    void update_MustNotUpdateAnUser_WhenNewPhoneAlreadyExists() {
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

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        UpdateUserRequestData data = new UpdateUserRequestData(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                "MALE",
                of(2000, 3, 15),
                null
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user)).thenReturn(empty());

        when(userRepository.findByCpf(anyString())).thenReturn(empty());

        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

        assertThrows(DuplicatedPhoneException.class, () -> userService.update(session, data));
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

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.findByEmail("martin-aparicio96@sp.senac.com.br"));
    }

    @Test
    void findByEmail_MustNotReturnAnUser_WhenEmailWasNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByEmail("martin-aparicio96@sp.senac.com.br"));
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

        when(userRepository.findByCpf(anyString())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.findByCpf("54463972906"));
    }

    @Test
    void findByCpf_MustNotReturnAnUser_WhenCpfWasNotFound() {
        when(userRepository.findByCpf(anyString())).thenReturn(empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByCpf("54463972906"));
    }

    @Test
    void getSessionOwner_MustReturnAnUser_WhenSessionOwnerWasFound() {
        Authentication session = new UsernamePasswordAuthenticationToken("bento@queirozgalvao.com", null);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertDoesNotThrow(() -> userService.getSessionOwner(session));
    }

    @Test
    void getSessionOwner_MustNotReturnAnUser_WhenSessionOwnerWasNotFound() {
        Authentication session = new UsernamePasswordAuthenticationToken("bento@queirozgalvao.com", null);

        when(userRepository.findByEmail(anyString())).thenReturn(empty());

        assertThrows(UserNotFoundException.class, () -> userService.getSessionOwner(session));
    }
}