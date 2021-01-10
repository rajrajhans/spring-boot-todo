package com.rajrajhans.SpringTodoApp.services.oauth2;

import com.rajrajhans.SpringTodoApp.models.AuthProvider;
import com.rajrajhans.SpringTodoApp.models.AuthUserDetails;
import com.rajrajhans.SpringTodoApp.models.User;
import com.rajrajhans.SpringTodoApp.models.auth.oauth2.OAuth2UserInfo;
import com.rajrajhans.SpringTodoApp.models.auth.oauth2.OAuth2UserInfoFactory;
import com.rajrajhans.SpringTodoApp.repositories.UserRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.security.core.AuthenticationException;
import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try{
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        }catch(AuthenticationException e){
            throw e;
        }catch(Exception e){
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User){
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOauth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            // We know this username/email

            user = userOptional.get();

            // Check if the same guy has signed up with some other Provider previously
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }

            // If not, that means he signed up with his email before, so update his account with oAuth info
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            // Email not found in the database, he is a new user
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return AuthUserDetails.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setUsername(oAuth2UserInfo.getEmail());
        user.setImageURL(oAuth2UserInfo.getImageUrl());
        user.setRole("ROLE_USER");
        user.setActive(true);

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setImageURL(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}

class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}