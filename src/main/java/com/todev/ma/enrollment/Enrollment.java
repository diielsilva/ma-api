package com.todev.ma.enrollment;

import com.todev.ma.order.Order;
import com.todev.ma.shared.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment extends Model {
    @OneToOne
    private Order order;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EnrollmentType type;

    public Enrollment() {
    }

    public Enrollment(Order order, EnrollmentType type) {
        this.order = order;
        this.type = type;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public EnrollmentType getType() {
        return type;
    }

    public void setType(EnrollmentType type) {
        this.type = type;
    }
}
