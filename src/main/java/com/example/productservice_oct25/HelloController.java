package com.example.productservice_oct25;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}/{times}")
    public String sayHello(@PathVariable ("name") String name, @PathVariable("times") int x) {
        String s= "";
        for(int  i=1;i<=x;i++){
            s= s + "Hello"+name +"!\n";
        }
        return s;
    }

    @GetMapping("/hi")
    public String sayHi(){
        return "Hi";
    }
}
