package com.rajrajhans.SpringTodoApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    public IndexController() {
    }
    @RequestMapping("/")
    public String index(){
        String markup = "<div style=\"text-align: center;\"><h1>Welcome to ToDo App REST API developed using Spring Boot</h1></div>";
        String markup1 = "<div style=\"text-align: center;\"><img src='https://c4.wallpaperflare.com/wallpaper/626/180/1004/hacking-elliot-mr-robot-hackerman-rami-malek-wallpaper-thumb.jpg'></div>";
        String markup2 = "<div style=\"text-align: center;\"><h3>Nice Job finding this, but there's nothing to see here. Head over to <a href=\"https://rajrajhans.com\">rajrajhans.com</a></h3></div>";
        return markup + markup1 + markup2;
    }
}
