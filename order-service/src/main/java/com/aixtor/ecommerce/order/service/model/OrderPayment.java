package com.aixtor.ecommerce.order.service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_payments")
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;   // CARD, UPI, WALLET
    private String paymentStatus;   // PENDING, SUCCESS, FAILED
    private String transactionId;
    private Double amount;

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    private LocalDateTime paymentDate;
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderPayment{" + "id=" + id + ", paymentMethod='" + paymentMethod + '\'' + ", paymentStatus='" + paymentStatus + '\'' + ", transactionId='" + transactionId + '\'' + ", order=" + order + '}';
    }
}
