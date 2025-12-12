package com.example.productservice_oct25.Controllers;

import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.Services.FakeStoreProductService;
import com.example.productservice_oct25.Services.ProductServices;
import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import com.example.productservice_oct25.commons.AuthCommons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.List;



@RestController
@RequestMapping("/products")
public class ProductController {

    private final AuthCommons authCommons;
    private final RestTemplate restTemplate;      // <-- LoadBalanced RestTemplate
    private final ProductServices productServices;

    public ProductController(ProductServices productServices,
                             RestTemplate restTemplate,      // <-- inject LoadBalanced bean
                             AuthCommons authCommons) {
        this.restTemplate = restTemplate;
        this.productServices = productServices;
        this.authCommons = authCommons;
    }

    //using fakestore service for elastic beanstalk deployment so commented below productservices
    //@Autowired
   //private FakeStoreProductService productServices;


  /*  @GetMapping("/{productId}/{tokenValue}")
    public ResponseEntity<?> getSingleProduct(
            @PathVariable("productId") Long productId,
            @PathVariable("tokenValue") String tokenValue)
            throws ProductNotFoundException {

        boolean valid = authCommons.validateToken(tokenValue);

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Token");
        }

        Product product = productServices.getSingleProduct(productId);
        return ResponseEntity.ok(product);
    }
*/
    // ✅ Get single product by ID
// Example: GET http://localhost:8080/products/1/<tokenValue>
    @GetMapping("/{productId}/{tokenValue}")
    public ResponseEntity<Product> getSingleProduct(
            @PathVariable("productId") Long productId,
            @PathVariable("tokenValue") String tokenValue)
            throws ProductNotFoundException {

        Product product = null;
        ResponseEntity<Product> responseEntity;

        // Validate token before fetching the product
        if (authCommons.validateToken(tokenValue)) {
            product = productServices.getSingleProduct(productId);
            responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(product, HttpStatus.UNAUTHORIZED);
        }
        return responseEntity;
    }
/*It initializes variables for product and responseEntity.
Calls AuthCommons.validateToken(tokenValue) to check if the token is valid.
If valid → fetches the product using productService.getSingleProduct(productId)
and returns 200 OK.If invalid → returns 401 Unauthorized response.*/

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProductWithoutToken(
            @PathVariable("productId") Long productId)
            throws ProductNotFoundException {

        Product product = productServices.getSingleProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/test/{productId}")
    public ResponseEntity<Product> getSingleProduct_UserServiceTest(
            @PathVariable Long productId) {

        System.out.println("Calling UserService from ProductService...");

        // Test call to UserService
        //restTemplate.getForEntity("http://UserServiceNov25/users/sample", Void.class);
        restTemplate.getForEntity("http://USERSERVICENOV25/users/sample", Void.class);

        // Fetch product
        Product product = productServices.getSingleProduct(productId);

        return ResponseEntity.ok(product);
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

//http://localhost:8080/products/title/iphone/1/20
//  iphone will be search query, so write method in service to search product by title
    @GetMapping("/title/{title}/{pageNumber}/{pageSize}") //searching product based on title
    public Page<Product> getProductsByTitle(@PathVariable("title") String title,
                                            @PathVariable("pageNumber") int pageNumber,
                                            @PathVariable("pageSize") int pageSize) {
        return productServices.getProductsByTitle(title, pageNumber, pageSize);

    }

}
