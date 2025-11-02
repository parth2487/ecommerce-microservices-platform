package com.aixtor.ecommerce.order.service.repository;


import com.aixtor.ecommerce.order.service.model.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {
    // Optional: find by orderId or transactionId
    OrderPayment findByOrderId(Long orderId);
    OrderPayment findByTransactionId(String transactionId);
//    OrderPayment findByOrderId(Long orderId);
}
