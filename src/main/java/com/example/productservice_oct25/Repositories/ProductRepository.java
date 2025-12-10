package com.example.productservice_oct25.Repositories;

import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.projections.ProductWithTitleAndPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.List;


@Repository  //(It also helps Spring handle database exceptions by converting them
// into Spring’s DataAccessException automatically.)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long aLong);
   /* When you search by ID, there’s a chance the product might not exist in the database.
    So Optional<Product> is used to safely handle null values and
    avoid NullPointerException.You can check with .isPresent() or .isEmpty()
    before using the value.
*/
    @Override
    List<Product> findAll();
    //When you fetch all products, the method always returns a List —
    // even if the table is empty.An empty list ([]) is returned instead of null,
    // so there’s no null pointer risk here.

//select * from products where title= 'some title'
    List<Product> findByTitle(String title);

    //select * from products where title like '%str%'
    List<Product> findByTitleContains(String str);

    // select * from products where title LIKE '%iPhone%' limit x offset y
//i want to search product by title,don't want complete so adding paging for to get limited result
    Page<Product> findByTitleContainsIgnoreCase(String str, Pageable pageable);


    //select * from products where title= 'some title' and price between 10 and 50
    //(if u want to add more param just add with And or Or
    List<Product> findByTitleIgnoreCaseAndPriceBetween
            (String title, double price, double price2);

    void deleteById(Long id);
    Product save(Product product);
    // Query: Find the title and price of products where title is 'iPhone' and price < 100000
// Equivalent SQL: SELECT title, price FROM products WHERE title = 'iPhone' AND price < 100000;
    @Query("SELECT p.title AS title, p.price AS price FROM Product p WHERE p.title = 'iPhone' AND p.price < 100000")
    List<ProductWithTitleAndPrice> findTitleAndPriceById();
    //we dont have any parameter a product with title and price so we created projections package
    //if we have return type as ProductWithTitleAndPrice interface, jpa hibernate will check this interface
    //for the getters and will map the result set to this interface type


    Optional<Product> findByCategory_Title(String categoryTitle);
    /*This method finds a product whose category’s title matches the given categoryTitle.
    It uses Spring Data JPA’s naming rule to automatically join Product
    with Category and fetch the matching record.*/
}

