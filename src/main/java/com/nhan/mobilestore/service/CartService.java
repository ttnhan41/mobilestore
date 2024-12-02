package com.nhan.mobilestore.service;

import com.nhan.mobilestore.dto.CartItemDTO;
import com.nhan.mobilestore.entity.Cart;
import com.nhan.mobilestore.entity.CartItem;
import com.nhan.mobilestore.entity.Product;
import com.nhan.mobilestore.entity.User;
import com.nhan.mobilestore.exception.NotFoundException;
import com.nhan.mobilestore.repository.CartItemRepository;
import com.nhan.mobilestore.repository.CartRepository;
import com.nhan.mobilestore.repository.ProductRepository;
import com.nhan.mobilestore.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TokenUtils tokenUtils;

    public Cart getUserCart(String bearerToken) {
        User user = tokenUtils.getUser(bearerToken);
        return cartRepository.findByUser(user).orElseGet(() -> createNewCart(user));
    }

    private Cart createNewCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart addProductToCart(CartItemDTO cartItemDTO, String bearerToken) {
        String productId = cartItemDTO.getProductId();
        int quantity = cartItemDTO.getQuantity();

        Cart cart = getUserCart(bearerToken);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        item.setProduct(product);
        item.setQuantity(item.getQuantity() + quantity);
        item.setCart(cart);

        if (!cart.getItems().contains(item)) {
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    public Cart updateCartItem(CartItemDTO cartItemDTO, String bearerToken) {
        String productId = cartItemDTO.getProductId();
        int quantity = cartItemDTO.getQuantity();

        Cart cart = getUserCart(bearerToken);
        CartItem item = findCartItem(cart, productId);
        item.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(CartItemDTO cartItemDTO, String bearerToken) {
        String productId = cartItemDTO.getProductId();

        Cart cart = getUserCart(bearerToken);
        CartItem item = findCartItem(cart, productId);
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return cartRepository.save(cart);
    }

    private CartItem findCartItem(Cart cart, String productId) {
        return cart.getItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + productId));
    }

    public void clearCart(String bearerToken) {
        Cart cart = getUserCart(bearerToken);

        while (!cart.getItems().isEmpty()) {
            CartItem item = cart.getItems().iterator().next();
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        }

        cartRepository.save(cart);
    }
}

