package com.spring.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @RequestMapping(value = {"","/","/index","/home"})
    public String displayHomePage(){
        return "home.html";
    }
}
