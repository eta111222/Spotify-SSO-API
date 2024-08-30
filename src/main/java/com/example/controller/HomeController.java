package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/homePage")
    public String home() {
        return "homePage"; 
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/userInfo"; 
    }

}
