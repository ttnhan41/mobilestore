package com.nhan.mobilestore.repository;

import com.nhan.mobilestore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
