package com.todev.ma.enrollment.confirmation;

import org.springframework.data.jpa.repository.JpaRepository;

interface ManualConfirmationRepository extends JpaRepository<ManualConfirmation, Long> {
}
