package com.celeguim.java_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String index() {
        String agora = new java.util.Date().toString();
        return agora + "<br>Hello World from SpringBoot";
    }
}