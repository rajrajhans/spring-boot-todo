package com.rajrajhans.SpringTodoApp.services.oauth2;

import com.rajrajhans.SpringTodoApp.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwtToken = generateJWTFromAuthentication(authentication);
        String redirectURL = "/";
        String targetURL = UriComponentsBuilder
                                .fromUriString(redirectURL)
                                .queryParam("token", jwtToken)
                                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetURL);
    }

    private String generateJWTFromAuthentication(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }
}
