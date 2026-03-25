package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.order.Order;
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
    private Order order;

    @ManyToOne
    private User confirmedBy;

    public ManualConfirmation() {
    }

    public ManualConfirmation(Order order, User confirmedBy) {
        this.order = order;
        this.confirmedBy = confirmedBy;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(User confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
}
