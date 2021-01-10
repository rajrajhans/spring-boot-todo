package com.rajrajhans.SpringTodoApp;

import com.rajrajhans.SpringTodoApp.filters.JwtRequestFilter;
import com.rajrajhans.SpringTodoApp.services.oauth2.OAuth2FailureHandler;
import com.rajrajhans.SpringTodoApp.services.oauth2.OAuth2SuccessHandler;
import com.rajrajhans.SpringTodoApp.services.oauth2.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Configuring Authentication
    private final UserDetailsService authUserDetailsService;
    private final OAuth2UserService oAuth2UserService;
    private final JwtRequestFilter jwtRequestFilter;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfiguration(UserDetailsService authUserDetailsService, OAuth2UserService oAuth2UserService, JwtRequestFilter jwtRequestFilter, OAuth2FailureHandler oAuth2FailureHandler, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.authUserDetailsService = authUserDetailsService;
        this.oAuth2UserService = oAuth2UserService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.oAuth2FailureHandler = oAuth2FailureHandler;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    // Configuring Authorisation

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new AuthEntryPoint())
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // telling spring security to not create a session
                    .and()
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/authenticate").permitAll()
                    .antMatchers("/") .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);   // telling spring security to make sure that our jwtreqfilter is called before the username and pwd auth filter is called
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
