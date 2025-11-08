package com.example.productservice_oct25.Exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
    public ProductNotFoundException() {
        super("Product not found");
    }
}
