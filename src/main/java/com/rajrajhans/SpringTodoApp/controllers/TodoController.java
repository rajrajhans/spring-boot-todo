package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.repositories.TodoRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                                     // telling Spring that this is a Controller for a REST API
public class TodoController {

    private final TodoRepository todorepo;          // Adding a "dependency" on the TodoRepository

    public TodoController(TodoRepository todorepo) {// Now when Spring instantiates this controller, it will also inject a TodoRepository instance
        this.todorepo = todorepo;
    }

    @RequestMapping("/todos")                    // mapping the route "/todos" to getTodos
    public String getTodos(Model model){            // The argument is basically a Model that we are going to return back to the view after adding our own things to it

        model.addAttribute("todos", todorepo.findAll());    // At runtime, when Spring gets a request at /todos, it is going to execute the getTodos method and it's going to provide that method a Model object, and our code is going to add a attribute called "todos" to that model by executing a findALl() on todorepo (which will give us a list of todos). Then, this model will return back to our view layer, and it is going to have an attribute called todos on it, then we'll be able to utilize that value inside of our view layer

        return "todos1";
    }
}
