package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.domains.User;
import com.rajrajhans.SpringTodoApp.services.UserService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{user_id}")
    public void updateUser(@RequestBody User user, @PathVariable Long user_id){
        userService.updateUser(user_id, user);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{user_id}")
    public void updateUser(@PathVariable Long user_id){
        userService.deleteUser(user_id);
    }




}
