package com.rajrajhans.SpringTodoApp.services;

import com.rajrajhans.SpringTodoApp.models.AuthUserDetails;
import com.rajrajhans.SpringTodoApp.models.User;
import com.rajrajhans.SpringTodoApp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    final UserRepository userRepo;

    public AuthUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));

        return user.map(AuthUserDetails::new).get();
    }
}
