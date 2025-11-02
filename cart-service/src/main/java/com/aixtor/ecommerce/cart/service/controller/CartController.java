package com.aixtor.ecommerce.cart.service.controller;

import com.aixtor.ecommerce.cart.service.model.Cart;
import com.aixtor.ecommerce.cart.service.model.CartItem;
import com.aixtor.ecommerce.cart.service.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService service) {
        this.cartService = service;
    }

    @PostMapping
    public Cart saveCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @PostMapping("/{userId}/items")
    public Cart addItem(@PathVariable String userId, @RequestBody CartItem item) {
        System.out.println("item.toString() :: "+item.toString());
        return cartService.addItem(userId, item);
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @DeleteMapping("/{userId}/items/{productId}/{variantId}")
    public Cart removeItem(@PathVariable String userId,
                           @PathVariable String productId,
                           @PathVariable String variantId) {
        return cartService.removeItem(userId, productId, variantId);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }
}
