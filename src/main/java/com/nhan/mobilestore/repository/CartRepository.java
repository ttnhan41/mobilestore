package com.nhan.mobilestore.repository;

import com.nhan.mobilestore.entity.Cart;
import com.nhan.mobilestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
