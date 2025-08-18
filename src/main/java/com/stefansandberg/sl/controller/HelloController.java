package com.stefansandberg.sl.controller;

import com.stefansandberg.sl.model.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public HelloResponse hello() {
        return new HelloResponse("Hello from Spring Boot!");
    }

    @GetMapping("/hello/{name}")
    public HelloResponse hello(@PathVariable String name) {
        return new HelloResponse("Hello " + name + "!");
    }
}
