package com.todev.ma.enrollment.order.confirmation;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.user.User;
import org.springframework.stereotype.Service;

@Service
class ManualConfirmationService implements IManualConfirmationService {
    private final ManualConfirmationRepository manualConfirmationRepository;

    ManualConfirmationService(ManualConfirmationRepository manualConfirmationRepository) {
        this.manualConfirmationRepository = manualConfirmationRepository;
    }

    @Override
    public void create(User admin, Order order) {
        ManualConfirmation confirmation = new ManualConfirmation(order, admin);

        manualConfirmationRepository.save(confirmation);
    }
}
