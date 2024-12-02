package com.nhan.mobilestore.mapper;

import com.nhan.mobilestore.dto.ProductDTO;
import com.nhan.mobilestore.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductQuantity(product.getProductQuantity());
        dto.setProductManufacturer(product.getProductManufacturer());
        dto.setProductCategory(product.getProductCategory());
        dto.setProductCondition(product.getProductCondition());
        dto.setProductImageUrl(product.getProductImageUrl());

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();

        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setProductPrice(dto.getProductPrice());
        product.setProductQuantity(dto.getProductQuantity());
        product.setProductManufacturer(dto.getProductManufacturer());
        product.setProductCategory(dto.getProductCategory());
        product.setProductCondition(dto.getProductCondition());
        product.setProductImageUrl(dto.getProductImageUrl());

        return product;
    }
}
