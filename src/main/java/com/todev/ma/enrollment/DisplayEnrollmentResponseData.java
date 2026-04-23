package com.todev.ma.enrollment;

import java.time.LocalDateTime;

record DisplayEnrollmentResponseData(
        Long id,
        String name,
        String cpf,
        String phone,
        Integer value,
        EnrollmentType type,
        LocalDateTime createdAt
) {
}
