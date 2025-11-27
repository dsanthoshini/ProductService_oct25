package com.example.productservice_oct25.Services;

import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import com.example.productservice_oct25.Model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductServices {

    // Get all products from the database
    List<Product> getAllproducts();

    // Get single product by ID — safer with Optional handling and custom exception
    //bcz we did self as primary service so productnotfoundexcetion must be handled
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    // Create new product
    Product createproduct(Product product);

    // Update/replace existing product
    Product replaceproduct(Long productId, Product product);

    // Delete product by ID
    Product deleteproduct(Long productId);

    //search product by title.
    // this is interface so implement this method in selfproductservice,fakestoreproduct service
    Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize);
}
