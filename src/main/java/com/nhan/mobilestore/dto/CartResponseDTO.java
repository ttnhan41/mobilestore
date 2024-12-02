package com.nhan.mobilestore.dto;

import com.nhan.mobilestore.entity.Cart;

public class CartResponseDTO {
    private String message;
    private Cart updatedCart;

    public CartResponseDTO(String message, Cart updatedCart) {
        this.message = message;
        this.updatedCart = updatedCart;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Cart getUpdatedCart() {
        return updatedCart;
    }

    public void setUpdatedCart(Cart updatedCart) {
        this.updatedCart = updatedCart;
    }
}

