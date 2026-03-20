package com.todev.ma.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface EnrollmentOrderRepository extends JpaRepository<EnrollmentOrder, Long> {
    List<EnrollmentOrder> findByUserId(long userId);
}
