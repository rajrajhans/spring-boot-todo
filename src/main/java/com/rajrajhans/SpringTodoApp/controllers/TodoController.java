package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.domains.Todo;
import com.rajrajhans.SpringTodoApp.repositories.TodoRepository;
import com.rajrajhans.SpringTodoApp.services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// telling Spring that this is a Controller for a REST API
public class TodoController {

    private final TodoService todoService;                           // Adding a "dependency" on the TodoService

    public TodoController(TodoRepository todorepo, TodoService todoService) { // Now when Spring instantiates this controller, it will also inject a TodoRepository instance
        this.todoService = todoService;
    }

    @RequestMapping("/todos/{id}")                                 // mapping the route "/todos" to getTodos
    public List<Todo> getTodo(@PathVariable String id) {              // The argument is basically a Model that we are going to return back to the view after adding our own things to it
        return todoService.getTodo(id);
    }

    @RequestMapping("/todos/")                                      // mapping the route "/todos" to getTodos
    public List<Todo> getTodos(@PathVariable String id) {              // The argument is basically a Model that we are going to return back to the view after adding our own things to it
        return todoService.getTodos();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/todos")
    public String createTodo(@RequestBody String todo) {
        return todoService.createTodo(todo);
    }

}
