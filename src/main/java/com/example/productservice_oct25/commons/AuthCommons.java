package com.example.productservice_oct25.commons;

import com.example.productservice_oct25.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Component
public  class AuthCommons {
    //call the userService to validate the token. for calling A to B need rest call
    //so inject resttemplate here
    private static RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //static method can access only static variable of a class so resttemplate is static here
    public static boolean validateToken(String tokenValue) {
        //call the userService to validate the token, we get it in responseentity so
        UserDto userDto = restTemplate.getForObject
                ("http://localhost:9000/users/validate/" + tokenValue, UserDto.class);
        //i want to capture the response of userDto class so use that class here.
        // we get in ResponseEntity
        if (userDto != null) {
            return true;
        }
        return false;
    }
}



/*
//using for ebs deployment so commentd above code
package com.example.productservice_oct25.commons;

import org.springframework.stereotype.Component;

@Component
public class AuthCommons {

    // Temporary validation for AWS Elastic Beanstalk deployment.
    // Always returns true so the service will not call localhost:8080 and fail.
    public static boolean validateToken(String tokenValue) {
        return true;
    }
}
*/
