package com.rajrajhans.SpringTodoApp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    public AdminController() {
    }

    @RequestMapping("/admin")
    public String admin(){
        return "<h1 style=\"text-align:center\">Admin Endpoint</h1>";
    }
}
