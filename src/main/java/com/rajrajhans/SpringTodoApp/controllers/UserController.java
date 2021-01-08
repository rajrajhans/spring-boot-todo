package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.models.User;
import com.rajrajhans.SpringTodoApp.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    public Long addUser(@RequestBody User user){
        return userService.addUser(user);
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
