package com.example.productservice_oct25;

import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.Repositories.ProductRepository;
import com.example.productservice_oct25.projections.ProductWithTitleAndPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.util.List;

@SpringBootTest
class ProductServiceOct25ApplicationTests {  // ✅ renamed to match your main app name

    @Autowired
    private ProductRepository productRepository;  // ✅ now recognized by Spring

    @Test
    public void testQuery() {
        List<ProductWithTitleAndPrice> products = productRepository.findTitleAndPriceById();

        for (ProductWithTitleAndPrice product : products) {
            System.out.println("Title: " + product.getTitle() + ", Price: " + product.getPrice());
        }

        Optional<Product> optionalProduct =
                productRepository.findByCategory_Title("mobiles");
        if (optionalProduct.isPresent()) {
            System.out.println("Product found: " + optionalProduct.get().getPrice());
        } else {
            System.out.println("No product found for this category.");
        }
    }
   /* It first fetches all products’ titles and prices using the projection query
    findTitleAndPriceById() and prints them.
            2️⃣ Then it searches for a product whose category title is “mobiles”
    using findByCategory_Title().
            3️⃣ If found, it prints that product’s price; otherwise, it prints “No
    product found for this category.”
*/
}
