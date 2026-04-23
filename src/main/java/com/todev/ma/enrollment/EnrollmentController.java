package com.todev.ma.enrollment;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/enrollments")
class EnrollmentController {
    private final EnrollmentService enrollmentService;

    EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @ResponseStatus(value = CREATED)
    void create(@RequestBody CreateEnrollmentRequestData data) {
        enrollmentService.create(data);
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping(value = "/{orderId}/confirm/manual")
    @ResponseStatus(value = CREATED)
    void create(Authentication session, @PathVariable Long orderId) {
        enrollmentService.create(session, orderId);
    }

    @GetMapping
    @ResponseStatus(value = OK)
    List<DisplayEnrollmentResponseData> findAllBySessionOwner(Authentication session) {
        return enrollmentService.findAllBySessionOwner(session);
    }
}
