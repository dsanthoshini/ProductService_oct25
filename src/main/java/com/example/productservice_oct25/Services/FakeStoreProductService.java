package com.example.productservice_oct25.Services;

import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import com.example.productservice_oct25.Model.Category;
import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.dtos.FakeStoreProductServiceDTOs;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
//@Primary
public class FakeStoreProductService implements ProductServices {

    private final RestTemplate restTemplate;

    // ✅ Constructor injection for RestTemplate
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ✅ Fetch all products from FakeStore API
    @Override
    public List<Product> getAllproducts() {

        // ✅ Make a GET request to fetch all products from FakeStore API
        ResponseEntity<FakeStoreProductServiceDTOs[]> responseEntity =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products",
                        FakeStoreProductServiceDTOs[].class
                );

        // ✅ Get the array of product DTOs from the API response
        FakeStoreProductServiceDTOs[] fakeStoreProductDtos = responseEntity.getBody();

        // ✅ Create an empty list to store converted Product objects
        List<Product> products = new ArrayList<>();

        // ✅ Loop through each product DTO and convert it to Product model
        if (fakeStoreProductDtos != null) {
            for (int i = 0; i < fakeStoreProductDtos.length; i++) {
                FakeStoreProductServiceDTOs fakeStoreProductDto = fakeStoreProductDtos[i];
                Product product = convertDTOToProduct(fakeStoreProductDto);
                products.add(product);
            }
        }

        // ✅ Return the list of all converted products
        return products;
    }

    // ✅ Fetch a single product by ID
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        // ✅ Make a GET request to FakeStore API to fetch a single product by its ID
        // Example: https://fakestoreapi.com/products/1
        ResponseEntity<FakeStoreProductServiceDTOs> responseEntity =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products/" + productId, // dynamic product ID
                        FakeStoreProductServiceDTOs.class                 // expected response type
                );

        // ✅ Extract the response body (DTO object)
        FakeStoreProductServiceDTOs productDTO = responseEntity.getBody();

        // ✅ Check if product exists
        if (productDTO == null) {
            throw new ProductNotFoundException(productId);
        }

        // ✅ Convert the FakeStore DTO → Product model using helper method
        return convertDTOToProduct(productDTO);
    }

    // ✅ Private helper method to convert FakeStore DTO → Product model
    private Product convertDTOToProduct(FakeStoreProductServiceDTOs productDTO) {
        if (productDTO == null) {
            return null; // safety check
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImageUrl(productDTO.getImageUrl()); // ✅ map DTO field correctly

        // ✅ Convert category string into Category object
        Category category = new Category();
        category.setTitle(productDTO.getCategory());
        product.setCategory(category);

        return product;
    }

    // ✅ Placeholder methods (can be implemented later)
    @Override
    public Product createproduct(Product product) {
        return null;
    }

    @Override
    public Product replaceproduct(Long productId, Product product) {
        return null;
    }

    @Override
    public Product deleteproduct(Long productId) {
        return null;
    }

    @Override
    public Page<Product> getProductsByTitle(String title, int pageNumber, int pageSize) {
        return null;
    }
}
