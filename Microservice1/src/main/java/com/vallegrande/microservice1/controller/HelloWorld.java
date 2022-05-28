package com.vallegrande.microservice1.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    private static final String template = "Hello, %s!";

    @RequestMapping("/nombre/{name}")
    public String nombre(@PathVariable("name") String name) {
        return String.format(template, name) ;
    }
}
