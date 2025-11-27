package com.example.productservice_oct25.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private Long userId;
        private String email;
//if u r getting valid userDto obj that means token is valid, if u r getting null token is invalid

}
/*You have two separate microservices — UserService and ProductService.
ProductService needs to validate a token before fetching a product (via singleProduct()
in ProductController).UserService already has a validateToken() method in UserController
 that returns a UserDto when the token is valid.ProductService will make an API call to
 UserService to validate the token.Whatever UserService returns (a UserDto object)
 should be captured by ProductService as well — so you create the same UserDto class on
 the ProductService side.If a valid UserDto is returned → token is valid;
 if null or unauthorized → token is invalid.
A GlobalExceptionHandler handles invalid tokens by returning a clear message
and HTTP 401 (Unauthorized) response.*/
