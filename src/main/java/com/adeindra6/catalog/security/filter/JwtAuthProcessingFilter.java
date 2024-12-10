package com.adeindra6.catalog.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.adeindra6.catalog.security.model.AnonymousAuthentication;
import com.adeindra6.catalog.security.model.JWTAuthenticationToken;
import com.adeindra6.catalog.security.model.RawAccessJWTToken;
import com.adeindra6.catalog.util.TokenExtractor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenExtractor tokenExtractor;

    private final AuthenticationFailureHandler failureHandler;

    public JwtAuthProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher, TokenExtractor tokenExtractor, AuthenticationFailureHandler failureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractor = tokenExtractor;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        String jwt = tokenExtractor.extract(authorizationHeader);

        RawAccessJWTToken rawToken = new RawAccessJWTToken(jwt);

        this.getAuthenticationManager().authenticate(new JWTAuthenticationToken(rawToken));

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        ctx.setAuthentication(authResult);
        SecurityContextHolder.setContext(ctx);

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());

        this.failureHandler.onAuthenticationFailure(request, response, failed);
        
        super.unsuccessfulAuthentication(request, response, failed);
    }
    
}
