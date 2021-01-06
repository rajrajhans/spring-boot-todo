package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.domains.Todo;
import com.rajrajhans.SpringTodoApp.services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// telling Spring that this is a Controller for a REST API
public class TodoController {

    private final TodoService todoService;                           // Adding a "dependency" on the TodoService

    public TodoController(TodoService todoService) { // Now when Spring instantiates this controller, it will also inject a TodoRepository instance
        this.todoService = todoService;
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/todos/{id}")                                 // mapping the route "/todos" to getTodos
    public Todo getTodo(@PathVariable Long id) {              // The argument is basically a Model that we are going to return back to the view after adding our own things to it
        return todoService.getTodo(id);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/todos")                                      // mapping the route "/todos" to getTodos
    public List<Todo> getTodos() {              // The argument is basically a Model that we are going to return back to the view after adding our own things to it
        return todoService.getTodos();
    }

    @RequestMapping(method = {RequestMethod.POST}, value = "/todos")
    public void addTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
    }

    @RequestMapping(method = {RequestMethod.PUT}, value = "/todos/{id}")
    public void updateTodo(@PathVariable Long id, @RequestBody Todo todo){
        todoService.updateTodo(id, todo);
    }

    @RequestMapping(method = {RequestMethod.DELETE}, value = "/todos/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }

    @RequestMapping(method = {RequestMethod.GET}, value = "/todos/user/{user_id}")
    public List<Todo> getTodosByUser(@PathVariable Long user_id){
        return todoService.getTodosByUser(user_id);
    }

}
