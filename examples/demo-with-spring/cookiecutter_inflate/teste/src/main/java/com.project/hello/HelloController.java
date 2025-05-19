package com.project.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/project-get")
    public String hello() {
        return "My new Hello World!";
    }
}
