package com.example.productservice_oct25.Controllers;

import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.Services.ProductServices;
import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }

    // ✅ Get single product by ID
    // Example: GET http://localhost:8080/products/1
    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") Long productId)
            throws ProductNotFoundException {
        return productServices.getSingleProduct(productId);
    }

    // ✅ Get all products
    // Example: GET http://localhost:8080/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productServices.getAllproducts();
    }

    // ✅ Create new product
    // Example: POST http://localhost:8080/products
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productServices.createproduct(product);
    }

    // ✅ Replace (update) existing product
    // Example: PUT http://localhost:8080/products/1
    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long productId,
                                  @RequestBody Product product) {
        return productServices.replaceproduct(productId, product);
    }

    // ✅ Delete product by ID (as query param)
    // Example: DELETE http://localhost:8080/products?id=1
    @DeleteMapping
    public Product deleteProduct(@RequestParam("id") Long productId) {
        return productServices.deleteproduct(productId);
    }
}
