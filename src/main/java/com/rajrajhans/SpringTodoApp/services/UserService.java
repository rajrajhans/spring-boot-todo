package com.rajrajhans.SpringTodoApp.services;

import com.rajrajhans.SpringTodoApp.domains.User;
import com.rajrajhans.SpringTodoApp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers(){
        List<User> allUsers = new ArrayList<User>();

        userRepo.findAll().forEach(allUsers::add);

        return allUsers;
    }

    public User getUser(Long id){
        Optional<User> res = userRepo.findById(id);
        return res.orElse(null);
    }

    public void addUser(User User){
        userRepo.save(User);
    }

    public void updateUser(Long id, User User){
        User.setId(id);
        userRepo.save(User);
    }

    public void deleteUser(Long id){
        userRepo.delete(getUser(id));
    }
}
