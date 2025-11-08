package com.example.productservice_oct25.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
//by using @bean anno spring recognize the method inside it
//and it will create only 1 object at a time by running new RestTemplate()
//if we want to mark class as a special calss use this annotation @configuration
// now spring recognize this class too
//for resttemplate we have multiple connection pools so we can perform so many http requests