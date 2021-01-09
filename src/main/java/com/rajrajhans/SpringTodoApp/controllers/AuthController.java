package com.rajrajhans.SpringTodoApp.controllers;

import com.rajrajhans.SpringTodoApp.models.AuthRequest;
import com.rajrajhans.SpringTodoApp.models.AuthResponse;
import com.rajrajhans.SpringTodoApp.services.AuthUserDetailsService;
import com.rajrajhans.SpringTodoApp.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Controller to handle JWT Auth endpoints
@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private AuthUserDetailsService authUserDetailsService;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, AuthUserDetailsService authUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authUserDetailsService = authUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            String username = authRequest.getUsername();
            String password = authRequest.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password", e);
        }

        final UserDetails userDetails = authUserDetailsService.loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
