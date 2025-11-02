package com.aixtor.ecommerce.order.service.controller;

import com.aixtor.ecommerce.order.service.DTO.OrderRequest;
import com.aixtor.ecommerce.order.service.DTO.OrderResponse;
import com.aixtor.ecommerce.order.service.model.OrderPayment;
import com.aixtor.ecommerce.order.service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService service) {
        this.orderService = service;
    }

    // Create a new order
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{userId}")
    public OrderResponse placeOrderFromCart(@PathVariable String userId, @RequestBody OrderRequest request) {
        return orderService.placeOrderFromCart(userId, request.getPaymentMethod());
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get all orders for a user
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Cancel an order
    @PutMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    // Add payment to an order
    @PostMapping("/{orderId}/payment")
    public OrderPayment addPayment(@PathVariable Long orderId, @RequestBody OrderPayment payment) {
        return orderService.addPayment(orderId, payment);
    }
}