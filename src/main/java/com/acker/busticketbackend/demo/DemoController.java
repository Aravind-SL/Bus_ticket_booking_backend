package com.acker.busticketbackend.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    String working() {
        return "Working Route";
    }
}
