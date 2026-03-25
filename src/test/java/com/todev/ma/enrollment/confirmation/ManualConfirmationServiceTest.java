package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.Enrollment;
import com.todev.ma.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(value = MockitoExtension.class)
class ManualConfirmationServiceTest {
    @InjectMocks
    private ManualConfirmationService manualConfirmationService;

    @Mock
    private ManualConfirmationRepository manualConfirmationRepository;

    @Test
    void create_MustCreateAManualConfirmation_WhenNoExceptionIsThrown() {
        User user = new User();
        Enrollment enrollment = new Enrollment();

        assertDoesNotThrow(() -> manualConfirmationService.create(user, enrollment));
    }
}