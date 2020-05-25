package com.pmu.util;

import com.google.common.collect.Maps;
import com.pmu.service.users.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;
import java.util.UUID;

public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    public static String USER_ID = "USER_ID";
    public static String EMAIL = "EMAIL";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Map<String, Object> tokenDetails = Maps.newHashMap();

        tokenDetails.put(USER_ID, user.getUserId());
        tokenDetails.put(EMAIL, user.getUsername());

        tokenDetails.put("jti", UUID.randomUUID().toString());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(tokenDetails);

        String encoded = super.encode(accessToken, authentication);
        ((DefaultOAuth2AccessToken) accessToken).setValue(encoded);
        return super.enhance(accessToken, authentication);
    }
}
