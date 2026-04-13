package com.todev.ma.order.pix;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface PixDetailRepository extends JpaRepository<PixDetail, Long> {
    Optional<PixDetail> findByOrderId(Long orderId);
}
