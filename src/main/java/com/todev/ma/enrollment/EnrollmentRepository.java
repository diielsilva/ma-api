package com.todev.ma.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
