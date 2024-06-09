package com.acker.busticketbackend.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String working() {
        System.out.println("Demo Working");
        return "Working Route";
    }
}
