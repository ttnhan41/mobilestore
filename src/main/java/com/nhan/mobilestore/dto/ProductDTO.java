package com.nhan.mobilestore.dto;

import com.nhan.mobilestore.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    private String productId;

    @NotNull(message = "Product name is required")
    private String productName;

    private String productDescription;

    @Min(value = 0, message = "Minimum product price is 0")
    private double productPrice;

    @Min(value = 0, message = "Minimum product quantity is 0")
    private int productQuantity;

    @NotNull(message = "Product manufacturer is required")
    private String productManufacturer;

    private String productCategory;

    @NotNull(message = "Product condition is required")
    private Product.Condition productCondition;

    @NotNull(message = "Product image url is required")
    private String productImageUrl;

    public ProductDTO() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public @NotNull(message = "Product name is required") String getProductName() {
        return productName;
    }

    public void setProductName(@NotNull(message = "Product name is required") String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Min(value = 0, message = "Minimum product price is 0")
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(@Min(value = 0, message = "Minimum product price is 0") double productPrice) {
        this.productPrice = productPrice;
    }

    @Min(value = 0, message = "Minimum product quantity is 0")
    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(@Min(value = 0, message = "Minimum product quantity is 0") int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public @NotNull(message = "Product manufacturer is required") String getProductManufacturer() {
        return productManufacturer;
    }

    public void setProductManufacturer(@NotNull(message = "Product manufacturer is required") String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public @NotNull(message = "Product condition is required") Product.Condition getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(@NotNull(message = "Product condition is required") Product.Condition productCondition) {
        this.productCondition = productCondition;
    }

    public @NotNull(message = "Product image url is required") String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(@NotNull(message = "Product image url is required") String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
