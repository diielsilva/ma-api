package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.Enrollment;
import com.todev.ma.shared.Model;
import com.todev.ma.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "manual_confirmations")
public class ManualConfirmation extends Model {
    @OneToOne
    private Enrollment enrollment;

    @ManyToOne
    private User confirmedBy;

    public ManualConfirmation() {
    }

    public ManualConfirmation(Enrollment enrollment, User confirmedBy) {
        this.enrollment = enrollment;
        this.confirmedBy = confirmedBy;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public User getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(User confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
}
