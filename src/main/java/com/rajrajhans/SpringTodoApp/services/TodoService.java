package com.rajrajhans.SpringTodoApp.services;

import com.rajrajhans.SpringTodoApp.domains.Todo;
import com.rajrajhans.SpringTodoApp.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    // CRUD Methods for our resource here
    private final TodoRepository todoRepo;          // Adding a "dependency" on the TodoRepository

    public TodoService(TodoRepository todorepo) {
        this.todoRepo = todorepo;
    }

    public List<Todo> getTodos() {
        List<Todo> allTodos = new ArrayList<>();

        todoRepo.findAll().forEach(allTodos::add);

        return allTodos;
    }

    public Todo getTodo(Long id){
        Optional<Todo> res = todoRepo.findById(id);
        return res.orElse(null);
    }

    public void addTodo(Todo todo){
        todoRepo.save(todo);
    }

    public void updateTodo(Long id, Todo todo){
        todo.setId(id);
        todoRepo.save(todo);
    }

    public void deleteTodo(Long id){
        todoRepo.delete(getTodo(id));
    }

    public List<Todo> getTodosByUser(Long user_id){
        return todoRepo.findByAuthorId(user_id);
    }
}
