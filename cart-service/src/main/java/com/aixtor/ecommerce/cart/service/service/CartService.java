package com.aixtor.ecommerce.cart.service.service;

import com.aixtor.ecommerce.cart.service.dto.Product;
import com.aixtor.ecommerce.cart.service.model.Cart;
import com.aixtor.ecommerce.cart.service.model.CartItem;
import com.aixtor.ecommerce.cart.service.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class CartService {

    private final CartRepository repository;
    private final WebClient.Builder webClient;

    public CartService(CartRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder;
    }

    public Cart getCart(String userId) {
        return repository.findByUserId(userId).orElse(new Cart(userId, List.of()));
    }

    // ✅ Save or update entire cart
    public Cart saveCart(Cart cart) {
        Cart existingCart = repository.findByUserId(cart.getUserId()).orElse(new Cart(cart.getUserId(), new ArrayList<>()));
        if (cart.getId() != null) {
            existingCart.setId(cart.getId());
        }
        existingCart.setItems(cart.getItems() != null ? cart.getItems() : new ArrayList<>());
        existingCart.setUpdatedAt(new Date());
        return repository.save(existingCart);
    }

    // ✅ Add single item
    public Cart addItem(String userId, CartItem item) {
        System.out.println("userId :: " + userId + " item ::" + item);
        Cart cart = repository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return newCart;
        });
        System.out.println("cart :: " + cart);
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        try {
            Product product = webClient.build().get()
                    .uri("http://localhost:8081/api/products/{id}", item.getProductId())
                    .retrieve()
                    .bodyToMono(Product.class)
                    .block();

            System.out.println("product is the :: " + product);
            if (product != null) {
                item.setProductName(product.getName());
                item.setPrice(product.getPrice());
            }
        } catch (Exception e) {
            System.out.println("There is an exception ::");
            e.printStackTrace();
        }

        // Remove duplicate product+variant
        cart.getItems().removeIf(i -> Objects.equals(i.getProductId(), item.getProductId())
                && Objects.equals(i.getVariantId(), item.getVariantId()));

        cart.getItems().add(item);
        cart.setUpdatedAt(new Date());
        return repository.save(cart);
    }

    // ✅ Remove single item
    public Cart removeItem(String userId, String productId, String variantId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> Objects.equals(i.getProductId(), productId) && Objects.equals(i.getVariantId(), variantId));

        cart.setUpdatedAt(new Date());
        return repository.save(cart);
    }

    // ✅ Clear whole cart
    public void clearCart(String userId) {
        repository.deleteByUserId(userId);
    }
}