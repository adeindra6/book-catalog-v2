package com.adeindra6.catalog.util;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;

@Component
public class JWTTokenHeaderExtractor implements TokenExtractor {

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String payload) {
        if(StringUtils.isBlank(payload)) {
            throw new AuthenticationServiceException("Authorization bearer should be provided");
        }

        if(payload.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header");
        }

        return payload.substring(HEADER_PREFIX.length(), payload.length());
    }
    
}
