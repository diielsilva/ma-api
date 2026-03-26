package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.Enrollment;
import com.todev.ma.user.User;

public interface IManualConfirmationService {
    void create(User admin, Enrollment enrollment);
}
