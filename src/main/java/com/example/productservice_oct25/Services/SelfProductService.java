package com.example.productservice_oct25.Services;

import com.example.productservice_oct25.Model.Category;
import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.Repositories.CategoryRepository;
import com.example.productservice_oct25.Repositories.ProductRepository;
import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary //bcz spring confuse b/w fake and self service which one to choose for injection
public class SelfProductService implements ProductServices {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        return optionalProduct.get();
    }
    /*This method fetches a single product by its ID from the database using the
    productRepository.It wraps the result in an Optional to handle missing data safely.
    If no product is found, it throws a ProductNotFoundException with that product ID.
    Otherwise, it returns the found Product object.*/
    @Override
    public Product createproduct(Product product) {
        // If the input product itself is missing, throw an exception —
        // this prevents null pointer errors.
        if (product == null) {
            throw new RuntimeException("Product data cannot be null");
        }

        // ✅ Step 2: Prevent invalid ID usage (to avoid overwriting)
        // If the incoming product already has an ID, it might indicate that
        // it exists in the database.
        // We first check whether such a product exists in DB. If not, we throw an error.
        // This ensures that only valid product IDs are used for updates,
        // not for creating new products.
        if (product.getId() != null) {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found with id " + product.getId());
            }
        }

        // ✅ Step 3: Handle category logic safely before saving product
        // Each product must belong to a category. So we check if a category object is provided in the request.
        // If the category exists, we check whether the same category title already exists in the database.
        // If it exists, we reuse it. If not, we create and save a new category.
        // This ensures that duplicate categories are not created each time a new product is added.
        Category category = product.getCategory();
        if (category != null && category.getTitle() != null) {
            // Check whether a category with the same title already exists in DB
            Optional<Category> optionalCategory =
                    categoryRepository.findByTitle(category.getTitle());

            if (optionalCategory.isEmpty()) {
                // If category doesn’t exist, save a new one and link it to the product
                Category savedCategory = categoryRepository.save(category);
                product.setCategory(savedCategory);
            } else {
                // If category exists, reuse the existing one
                product.setCategory(optionalCategory.get());
            }
        } else {
            // If category or its title is null, throw a validation exception.
            // This ensures product always belongs to a valid category.
            throw new RuntimeException("Category title cannot be null");
        }

        // ✅ Step 4: Save the final product object into the database
        // At this point, the product has a valid category linked to it
        // (either existing or newly created).
        // The save() method automatically inserts or updates the
        // product in the DB based on its ID.
        return productRepository.save(product);
    }


    @Override
    public Product replaceproduct(Long Productid, Product product) {
        return null;
    }

    @Override
    public Product deleteproduct(Long Productid) {
        return null;
    }


}
