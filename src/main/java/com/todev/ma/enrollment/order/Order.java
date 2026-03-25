package com.todev.ma.enrollment.order;

import com.todev.ma.shared.Model;
import com.todev.ma.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends Model {
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime paidAt;

    public Order() {
    }

    public Order(
            User user, Integer value,
            LocalDateTime dueDate,
            PaymentMethod paymentMethod,
            OrderStatus status
    ) {
        this.user = user;
        this.value = value;
        this.dueDate = dueDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Order(
            User user,
            Integer value,
            LocalDateTime dueDate,
            PaymentMethod paymentMethod,
            OrderStatus status,
            LocalDateTime paidAt
    ) {
        this.user = user;
        this.value = value;
        this.dueDate = dueDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.paidAt = paidAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
