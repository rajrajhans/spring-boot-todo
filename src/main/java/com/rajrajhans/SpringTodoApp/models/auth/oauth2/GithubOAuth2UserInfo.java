package com.rajrajhans.SpringTodoApp.models.auth.oauth2;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        String name =  (String) attributes.get("name");

        if(name == null){
            name = (String) attributes.get("login");
        }

        return name;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("login");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }

    @Override
    public String toString() {
        return "GithubOAuth2UserInfo{" +
                "attributes=" + attributes.toString() +
                '}';
    }
}
