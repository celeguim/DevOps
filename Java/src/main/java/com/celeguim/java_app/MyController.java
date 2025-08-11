package com.celeguim.java_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;

@RestController
public class MyController  {

    @GetMapping("/")
    public String index() throws java.net.UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();
        String agora = new java.util.Date().toString();
        return agora + "<br>" + hostname + "<br>Hello World from SpringBoot";
    }
}