package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.Enrollment;
import com.todev.ma.user.User;
import org.springframework.stereotype.Service;

@Service
class ManualConfirmationService implements IManualConfirmationService {
    private final ManualConfirmationRepository confirmationRepository;

    ManualConfirmationService(ManualConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void create(User admin, Enrollment enrollment) {
        ManualConfirmation confirmation = new ManualConfirmation(enrollment, admin);

        confirmationRepository.save(confirmation);
    }
}
