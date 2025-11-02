package com.aixtor.ecommerce.order.service.service;
//
//import com.aixtor.ecommerce.order.service.DTO.OrderRequest;
//import com.aixtor.ecommerce.order.service.DTO.OrderResponse;
//import com.aixtor.ecommerce.order.service.model.*;
//import com.aixtor.ecommerce.order.service.repository.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//    private final OrderPaymentRepository orderPaymentRepository;
//    private final WebClient.Builder webClientBuilder;
//
//    public OrderService(OrderRepository orderRepository,
//                        OrderItemRepository orderItemRepository,
//                        OrderPaymentRepository orderPaymentRepository,
//                        WebClient.Builder webClientBuilder) {
//        this.orderRepository = orderRepository;
//        this.orderItemRepository = orderItemRepository;
//        this.orderPaymentRepository = orderPaymentRepository;
//        this.webClientBuilder = webClientBuilder;
//    }
//
////    public OrderResponse placeOrder(OrderRequest request) {
////        // 1. Validate products from Product Service
////        double total = request.getItems().stream()
////                .mapToDouble(item -> {
////                    Double price = webClientBuilder.build()
////                            .get()
////                            .uri("http://product-service/api/products/{id}/price", item.getProductId())
////                            .retrieve()
////                            .bodyToMono(Double.class)
////                            .block();
////                    return price * item.getQuantity();
////                }).sum();
////
////        // 2. Save Order
////        Order order = new Order();
////        order.setUserId(request.getUserId());
////        order.setStatus("PLACED");
////        order.setTotalAmount(total);
////        order = orderRepository.save(order);
////
////        // 3. Save Order Items
////        List<OrderItem> items = request.getItems().stream().map(i -> {
////            OrderItem oi = new OrderItem();
////            oi.setOrder(order);
////            oi.setProductId(i.getProductId());
////            oi.setVariantId(i.getVariantId());
////            oi.setQuantity(i.getQuantity());
////
////            Double price = webClientBuilder.build()
////                    .get()
////                    .uri("http://product-service/api/products/{id}/price", i.getProductId())
////                    .retrieve()
////                    .bodyToMono(Double.class)
////                    .block();
////
////            oi.setUnitPrice(price);
////            return oi;
////        }).collect(Collectors.toList());
////
////        orderItemRepository.saveAll(items);
////        order.setItems(items);
////
////        // 4. Save Payment
////        OrderPayment payment = new OrderPayment();
////        payment.setOrder(order);
////        payment.setPaymentMethod(request.getPaymentMethod());
////        payment.setPaymentStatus("PENDING");
////        payment.setTransactionId(UUID.randomUUID().toString());
////        orderPaymentRepository.save(payment);
////        order.setPayment(payment);
////
////        // 5. Build Response
////        OrderResponse response = new OrderResponse();
////        response.setOrderId(order.getId());
////        response.setStatus(order.getStatus());
////        response.setTotalAmount(order.getTotalAmount());
////        response.setPaymentStatus(payment.getPaymentStatus());
////        response.setItems(items.stream().map(oi -> {
////            OrderResponse.OrderItemResponse ir = new OrderResponse.OrderItemResponse();
////            ir.setProductId(oi.getProductId());
////            ir.setVariantId(oi.getVariantId());
////            ir.setQuantity(oi.getQuantity());
////            ir.setUnitPrice(oi.getUnitPrice());
////            return ir;
////        }).collect(Collectors.toList()));
////
////        return response;
////    }
//
//    public OrderResponse placeOrder(OrderRequest request) {
//        List<OrderItem> items = new ArrayList<>();
//        double total = 0.0;
//
//        // Loop through requested items and fetch prices from product-service
//        for (OrderRequest.Item i : request.getItems()) {
//            Double price = webClientBuilder.build()
//                    .get()
//                    .uri("http://product-service/api/products/{id}/price", i.getProductId())
//                    .retrieve()
//                    .bodyToMono(Double.class)
//                    .block();
//
//            if (price == null) {
//                throw new RuntimeException("Price not found for product: " + i.getProductId());
//            }
//
//            OrderItem oi = new OrderItem();
//            oi.setProductId(i.getProductId());
//            oi.setVariantId(i.getVariantId());
//            oi.setQuantity(i.getQuantity());
//            oi.setUnitPrice(price);
//            items.add(oi);
//
//            total += price * i.getQuantity();
//        }
//
//        // Create Order
//        Order order = new Order();
//        order.setUserId(request.getUserId());
//        order.setItems(items);
//        order.setTotalAmount(total);
//        order.setStatus("PENDING");
//        order.setCreatedAt(LocalDateTime.now());
//
//        // Save Order first
//        Order savedOrder = orderRepository.save(order);
//
//        // Save payment info
//        OrderPayment payment = new OrderPayment();
//        payment.setOrder(savedOrder);
//        payment.setPaymentMethod(request.getPaymentMethod());
//        payment.setAmount(total);
//        payment.setPaymentStatus("PENDING"); // could be "SUCCESS" after actual gateway call
//        payment.setPaymentDate(LocalDateTime.now());
//        orderPaymentRepository.save(payment);
//
//        // Build response
//        List<OrderResponse.OrderItemResponse> itemResponses = new ArrayList<>();
//        for (OrderItem oi : items) {
//            OrderResponse.OrderItemResponse itemResp = new OrderResponse.OrderItemResponse();
//            itemResp.setProductId(oi.getProductId());
//            itemResp.setVariantId(oi.getVariantId());
//            itemResp.setQuantity(oi.getQuantity());
//            itemResp.setUnitPrice(oi.getUnitPrice());
//            itemResponses.add(itemResp);
//        }
//        OrderResponse response = new OrderResponse();
//        response.setOrderId(savedOrder.getId());
//        response.setTotalAmount(total);
//        response.setStatus(savedOrder.getStatus());
//        response.setItems(itemResponses);
//
//        return response;
//    }
//
//}































import com.aixtor.ecommerce.order.service.DTO.Cart;
import com.aixtor.ecommerce.order.service.DTO.CartItem;
import com.aixtor.ecommerce.order.service.DTO.OrderRequest;
import com.aixtor.ecommerce.order.service.DTO.OrderResponse;
import com.aixtor.ecommerce.order.service.model.*;
import com.aixtor.ecommerce.order.service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OrderPaymentRepository orderPaymentRepository,
                        WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.webClientBuilder = webClientBuilder;
    }

    // Create a new order
    public OrderResponse createOrder(OrderRequest request) {

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        // Fetch product prices from Product Service
        for (OrderRequest.Item i : request.getItems()) {
            Double price = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/products/{id}/price", i.getProductId())
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            if (price == null) {
                throw new RuntimeException("Product price not found for: " + i.getProductId());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(i.getProductId());
            orderItem.setVariantId(i.getVariantId());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setUnitPrice(price);

            items.add(orderItem);
            total += price * i.getQuantity();
        }

        // Create Order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // Save initial payment
        OrderPayment payment = new OrderPayment();
        payment.setOrder(savedOrder);
        payment.setAmount(total);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        orderPaymentRepository.save(payment);

        // Prepare response
        List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(oi -> {
            OrderResponse.OrderItemResponse resp = new OrderResponse.OrderItemResponse();
            resp.setProductId(oi.getProductId());
            resp.setVariantId(oi.getVariantId());
            resp.setQuantity(oi.getQuantity());
            resp.setUnitPrice(oi.getUnitPrice());
            return resp;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setTotalAmount(total);
        response.setStatus(savedOrder.getStatus());
        response.setItems(itemResponses);

        return response;
    }

    // Get order by ID
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream().map(oi -> {
            OrderResponse.OrderItemResponse resp = new OrderResponse.OrderItemResponse();
            resp.setProductId(oi.getProductId());
            resp.setVariantId(oi.getVariantId());
            resp.setQuantity(oi.getQuantity());
            resp.setUnitPrice(oi.getUnitPrice());
            return resp;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setItems(itemResponses);

        return response;
    }

    // Get all orders for a user
    public List<OrderResponse> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(o -> getOrderById(o.getId())).collect(Collectors.toList());
    }

    // Cancel order
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return getOrderById(orderId);
    }

    // Add payment to an order
    public OrderPayment addPayment(Long orderId, OrderPayment payment) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setOrder(order);
        payment.setPaymentDate(LocalDateTime.now());
        return orderPaymentRepository.save(payment);
    }


    public OrderResponse placeOrderFromCart(String userId, String paymentMethod) {
        // 1. Get cart from Cart Service
        Cart cart = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/carts/{userId}", userId)
                .retrieve()
                .bodyToMono(Cart.class)
                .block();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty for user: " + userId);
        }

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        // 2. Validate each cart item with Product Service
        for (CartItem ci : cart.getItems()) {
            Double price = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/products/{id}/price", ci.getProductId())
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

//            Integer stock = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8081/api/products/{id}/stock", ci.getProductId())
//                    .retrieve()
//                    .bodyToMono(Integer.class)
//                    .block();

            Integer stock = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/products/{id}/variants/{variantId}/stock",
                            ci.getProductId(), ci.getVariantId())
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            if (price == null) {
                throw new RuntimeException("Price not found for product: " + ci.getProductId());
            }
            if (stock == null || stock < ci.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + ci.getProductId());
            }

            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getProductId());
            oi.setVariantId(ci.getVariantId());
            oi.setQuantity(ci.getQuantity());
            oi.setUnitPrice(price);

            items.add(oi);
            total += price * ci.getQuantity();
        }

        // 3. Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 4. Save Payment
        OrderPayment payment = new OrderPayment();
        payment.setOrder(savedOrder);
        payment.setAmount(total);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        orderPaymentRepository.save(payment);

        // 5. Clear the cart after order placed
        webClientBuilder.build()
                .delete()
                .uri("http://localhost:8082/api/carts/{userId}", userId)
                .retrieve()
                .toBodilessEntity()
                .block();


        // 4. Reduce stock for each variant
        for (CartItem ci : cart.getItems()) {
            webClientBuilder.build()
                    .put()
                    .uri("http://localhost:8081/api/products/{id}/variants/{variantId}/stock?quantityToReduce={qty}",
                            ci.getProductId(), ci.getVariantId(), ci.getQuantity())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        // 6. Build Response
        List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(oi -> {
            OrderResponse.OrderItemResponse resp = new OrderResponse.OrderItemResponse();
            resp.setProductId(oi.getProductId());
            resp.setVariantId(oi.getVariantId());
            resp.setQuantity(oi.getQuantity());
            resp.setUnitPrice(oi.getUnitPrice());
            return resp;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setTotalAmount(total);
        response.setStatus(savedOrder.getStatus());
        response.setItems(itemResponses);

        return response;
    }

}
/*
* package com.aixtor.ecommerce.order.service.service;

import com.aixtor.ecommerce.order.service.DTO.Cart;
import com.aixtor.ecommerce.order.service.DTO.CartItem;
import com.aixtor.ecommerce.order.service.DTO.OrderRequest;
import com.aixtor.ecommerce.order.service.DTO.OrderResponse;
import com.aixtor.ecommerce.order.service.model.*;
import com.aixtor.ecommerce.order.service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final WebClient productWebClient;
    private final WebClient cartWebClient;
    private final WebClient inventoryWebClient;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        OrderPaymentRepository orderPaymentRepository,
                        WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderPaymentRepository = orderPaymentRepository;

        // Reuse WebClient instances for microservices
        this.productWebClient = webClientBuilder.baseUrl("http://localhost:8081").build();
        this.cartWebClient = webClientBuilder.baseUrl("http://localhost:8082").build();
        this.inventoryWebClient = webClientBuilder.baseUrl("http://localhost:8083").build(); // inventory service
    }

    // Place an order from cart
    public OrderResponse placeOrderFromCart(String userId, String paymentMethod) {
        // 1. Get cart
        Cart cart = cartWebClient.get()
                .uri("/api/carts/{userId}", userId)
                .retrieve()
                .bodyToMono(Cart.class)
                .block();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty for user: " + userId);
        }

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        // 2. Validate each cart item with ProductService & InventoryService
        for (CartItem ci : cart.getItems()) {
            Double price = productWebClient.get()
                    .uri("/api/products/{id}/variants/{variantId}/price", ci.getProductId(), ci.getVariantId())
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block();

            Integer stock = inventoryWebClient.get()
                    .uri("/api/inventory/{productId}/{variantId}", ci.getProductId(), ci.getVariantId())
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            if (price == null) throw new RuntimeException("Price not found for product: " + ci.getProductId());
            if (stock == null || stock < ci.getQuantity())
                throw new RuntimeException("Not enough stock for product: " + ci.getProductId());

            // Reserve stock before placing order
            inventoryWebClient.post()
                    .uri("/api/inventory/reserve?productId={productId}&variantId={variantId}&quantity={qty}",
                            ci.getProductId(), ci.getVariantId(), ci.getQuantity())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getProductId());
            oi.setVariantId(ci.getVariantId());
            oi.setQuantity(ci.getQuantity());
            oi.setUnitPrice(price);

            items.add(oi);
            total += price * ci.getQuantity();
        }

        // 3. Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        order.setTotalAmount(total);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 4. Save Payment
        OrderPayment payment = new OrderPayment();
        payment.setOrder(savedOrder);
        payment.setAmount(total);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        orderPaymentRepository.save(payment);

        // 5. Clear Cart
        cartWebClient.delete()
                .uri("/api/carts/{userId}", userId)
                .retrieve()
                .toBodilessEntity()
                .block();

        // 6. Build Response
        List<OrderResponse.OrderItemResponse> itemResponses = items.stream().map(oi -> {
            OrderResponse.OrderItemResponse resp = new OrderResponse.OrderItemResponse();
            resp.setProductId(oi.getProductId());
            resp.setVariantId(oi.getVariantId());
            resp.setQuantity(oi.getQuantity());
            resp.setUnitPrice(oi.getUnitPrice());
            return resp;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setTotalAmount(total);
        response.setStatus(savedOrder.getStatus());
        response.setItems(itemResponses);

        return response;
    }

    // Get order by ID
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream().map(oi -> {
            OrderResponse.OrderItemResponse resp = new OrderResponse.OrderItemResponse();
            resp.setProductId(oi.getProductId());
            resp.setVariantId(oi.getVariantId());
            resp.setQuantity(oi.getQuantity());
            resp.setUnitPrice(oi.getUnitPrice());
            return resp;
        }).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setItems(itemResponses);

        return response;
    }

    // Cancel an order
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        // Release reserved stock
        for (OrderItem oi : order.getItems()) {
            inventoryWebClient.post()
                    .uri("/api/inventory/release?productId={productId}&variantId={variantId}&quantity={qty}",
                            oi.getProductId(), oi.getVariantId(), oi.getQuantity())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        return getOrderById(orderId);
    }

}

*
* */