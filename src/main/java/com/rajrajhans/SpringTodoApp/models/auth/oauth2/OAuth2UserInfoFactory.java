package com.rajrajhans.SpringTodoApp.models.auth.oauth2;

import com.rajrajhans.SpringTodoApp.models.AuthProvider;

import java.util.Map;

// To handle multiple providers and return appropriate UserInfo object
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOauth2UserInfo(String regID, Map<String, Object> attributes){
        if(regID.equalsIgnoreCase(AuthProvider.github.toString())){
            return new GithubOAuth2UserInfo(attributes);
        }else{
            return null;
        }
    }
}
