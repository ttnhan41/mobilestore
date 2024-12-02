package com.nhan.mobilestore.controller;

import com.nhan.mobilestore.dto.CartItemDTO;
import com.nhan.mobilestore.dto.CartResponseDTO;
import com.nhan.mobilestore.entity.Cart;
import com.nhan.mobilestore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String bearerToken) {
        Cart cart = cartService.getUserCart(bearerToken);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addProductToCart(@RequestBody CartItemDTO cartItemDTO,
                                              @RequestHeader("Authorization") String bearerToken) {
        Cart updatedCart = cartService.addProductToCart(cartItemDTO, bearerToken);
        CartResponseDTO response =
                new CartResponseDTO("Product added to cart successfully", updatedCart);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponseDTO> updateCartItem(@RequestBody CartItemDTO cartItemDTO,
                                                          @RequestHeader("Authorization") String bearerToken) {
        Cart updatedCart = cartService.updateCartItem(cartItemDTO, bearerToken);
        CartResponseDTO response =
                new CartResponseDTO("Product quantity updated successfully", updatedCart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(@RequestBody CartItemDTO cartItemDTO,
                                                                 @RequestHeader("Authorization") String bearerToken) {
        Cart updatedCart = cartService.removeProductFromCart(cartItemDTO, bearerToken);
        CartResponseDTO response =
                new CartResponseDTO("Product removed from cart successfully", updatedCart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader("Authorization") String bearerToken) {
        cartService.clearCart(bearerToken);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}

