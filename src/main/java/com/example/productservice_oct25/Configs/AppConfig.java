package com.example.productservice_oct25.Configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

//by using @bean anno spring recognize the method inside it
//and it will create only 1 object at a time by running new RestTemplate()
//if we want to mark class as a special calss use this annotation @configuration
// now spring recognize this class too
//for resttemplate we have multiple connection pools so we can perform so many http requests

    @Bean
    public RedisTemplate<String, Object> createRedisTemplate
            (RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
/*for http call we need resttemplace same like to call redis we need client so
we should create redistemplate as bean. creating common bean and use where ever u want
so goto appnconfig class and add method and mention it as bean.
redis is key value data pair so RedisTemplate<String, object> and redis need connection factory
in input. create obj of redistemplace, set the connection factory,return redis template*/
    }

    // ADD THIS NEW BEAN BELOW (normal RestTemplate – for fakestoreapi)
    @Bean
    public RestTemplate normalRestTemplate() {
        return new RestTemplate();
    }
}