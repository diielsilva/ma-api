package com.todev.ma.enrollment;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/enrollments")
class EnrollmentController {
    private final EnrollmentService enrollmentService;

    EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    void create(@RequestBody CreateEnrollmentRequestData data) {
        enrollmentService.create(data);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping(value = "/{orderId}/confirm/manual")
    void create(Authentication session, @PathVariable Long orderId) {
        enrollmentService.create(session, orderId);
    }
}
