package com.projarq.trabalho01_clean;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    public Controller() {}

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String helloWorld() {
        return "Hello, World!";
    }
}
