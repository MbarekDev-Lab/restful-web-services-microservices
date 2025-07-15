package com.rest_api_webservices.microservices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello, world!";
    }
}
