package com.example.myproject.config.oauth.provider;

public interface OAuth2UserInfo{
    String getProvider();
    String getEmail();
    String getName();
}
