package com.rajrajhans.SpringTodoApp.filters;

import com.rajrajhans.SpringTodoApp.models.AuthUserDetails;
import com.rajrajhans.SpringTodoApp.services.AuthUserDetailsService;
import com.rajrajhans.SpringTodoApp.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// What we want to do here is intercept every request once and examine the header.

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private AuthUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    public JwtRequestFilter(AuthUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // here, we want to examine the incoming request for JWT in the header, and validate it
        // if it is valid, we want to extract UserDetails and set it in execution context

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            AuthUserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(jwt, userDetails)){

                // the lines below are what would have happened by default. But, we are just making them happen under the condition that the token is valid. Before, the lines below would be the default behaviour for all requests. Since we are overriding, we are doing it (only in case of a valid jwt)

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);        // handing off the control to the next filter in the control chain
    }
}
