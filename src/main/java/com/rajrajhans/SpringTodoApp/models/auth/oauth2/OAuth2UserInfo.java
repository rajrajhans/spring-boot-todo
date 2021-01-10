package com.rajrajhans.SpringTodoApp.models.auth.oauth2;

import java.util.Map;

// Contact for OAuthUserInfo for different providers. Every OAuth Provider returns a different JSON response.
// Using this class and its implementations, we will standardize it to match with our convention
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
