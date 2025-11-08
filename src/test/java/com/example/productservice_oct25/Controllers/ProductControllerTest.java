package com.example.productservice_oct25.Controllers;

import com.example.productservice_oct25.Exceptions.ProductNotFoundException;
import com.example.productservice_oct25.Model.Product;
import com.example.productservice_oct25.Services.ProductServices;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductServices productServices;

    @Test
    public void testGetSingleProduct() {
        Long prouctId = 10L;
        Product Expected = productController.getSingleProduct(prouctId);
        when(productServices.getSingleProduct(prouctId)).thenReturn(Expected);
        Product actual = productController.getSingleProduct(prouctId);
        assertEquals(Expected, actual, "products are not same");
    }

    @Test
    public void testGetSingleInvalidId(){
        Long productId = -1L;
        when(productServices.getSingleProduct(productId)).
                thenThrow(new ProductNotFoundException());

        assertThrows(ProductNotFoundException.class,
                () -> productController.getSingleProduct(productId));
        /*When the test runs, it tries to call the service method getSingleProduct(-1L).
But since we used when(...).thenThrow(...), Mockito is instructed to
 immediately throw ProductNotFoundException whenever this ID is used — no real logic runs.
Then, the controller also tries to call this mocked service method with the same ID.
As a result, the controller receives the same exception thrown by the mock,
and assertThrows checks that this behavior is correct.*/
    }

}