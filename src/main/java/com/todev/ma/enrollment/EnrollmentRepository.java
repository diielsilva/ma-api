package com.todev.ma.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByOrderUserId(Long userId);
}
