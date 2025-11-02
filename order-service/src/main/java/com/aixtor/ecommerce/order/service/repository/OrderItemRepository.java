package com.aixtor.ecommerce.order.service.repository;

import com.aixtor.ecommerce.order.service.model.Order;
import com.aixtor.ecommerce.order.service.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}