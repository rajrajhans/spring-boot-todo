package com.rajrajhans.SpringTodoApp.services;

import com.rajrajhans.SpringTodoApp.domains.Todo;
import com.rajrajhans.SpringTodoApp.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    // CRUD Methods for our resource here
    private final TodoRepository todorepo;          // Adding a "dependency" on the TodoRepository

    public TodoService(TodoRepository todorepo) {
        this.todorepo = todorepo;
    }

    public List<Todo> getTodo(String id) {
        List<Todo> allTodos = new ArrayList<>();

        return allTodos;
    }

    public List<Todo> getTodos() {
        List<Todo> allTodos = new ArrayList<>();
        todorepo.findAll().forEach(allTodos::add);
        return allTodos;
    }

    public String createTodo(String todo) {
        return todo + " created";
    }
}
