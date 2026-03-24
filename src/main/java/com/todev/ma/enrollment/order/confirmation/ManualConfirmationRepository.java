package com.todev.ma.enrollment.order.confirmation;

import org.springframework.data.jpa.repository.JpaRepository;

interface ManualConfirmationRepository extends JpaRepository<ManualConfirmation, Long> {
}
