package com.nhan.mobilestore.service;

import com.nhan.mobilestore.dto.ProductDTO;
import com.nhan.mobilestore.entity.Product;
import com.nhan.mobilestore.exception.NotFoundException;
import com.nhan.mobilestore.mapper.ProductMapper;
import com.nhan.mobilestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDTO);
    }

    public ProductDTO getProductById(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
        return ProductMapper.toDTO(product);
    }

    public Product saveProduct(Product product) {
        // Generate a product ID if not provided
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(generateProductId());
        }
        return productRepository.save(product);
    }

    private String generateProductId() {
        return "MOBI-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = ProductMapper.toEntity(productDTO);
        Product createdProduct = saveProduct(product);

        return ProductMapper.toDTO(createdProduct);
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductQuantity(productDTO.getProductQuantity());
        product.setProductManufacturer(productDTO.getProductManufacturer());
        product.setProductCategory(productDTO.getProductCategory());
        product.setProductCondition(productDTO.getProductCondition());
        product.setProductImageUrl(productDTO.getProductImageUrl());

        Product updatedProduct = saveProduct(product);
        return ProductMapper.toDTO(updatedProduct);
    }

    public void deleteProduct(String id){
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }
}


