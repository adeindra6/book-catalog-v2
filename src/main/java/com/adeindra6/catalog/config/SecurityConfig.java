package com.adeindra6.catalog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.adeindra6.catalog.security.filter.JwtAuthProcessingFilter;
import com.adeindra6.catalog.security.filter.UsernamePasswordAuthProcessingFilter;
import com.adeindra6.catalog.security.handler.UsernamePasswordAuthFailureHandler;
import com.adeindra6.catalog.security.handler.UsernamePasswordAuthSuccessHandler;
import com.adeindra6.catalog.security.provider.JWTAuthenticationProvider;
import com.adeindra6.catalog.security.provider.UsernamePasswordAuthProvider;
import com.adeindra6.catalog.security.util.JWTTokenFactory;
import com.adeindra6.catalog.security.util.SkipPathRequestMatcher;
import com.adeindra6.catalog.util.TokenExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfiguration {

    private final static String AUTH_URL = "/v1/login";

    private final static String V1_URL = "/v1/**";

    private final static String V2_URL = "/v2/**";

    private final static List<String> PERMIT_ENDPOINT_LIST = Arrays.asList(AUTH_URL, "/**");

    private final static List<String> AUTHENTICATED_ENDPOINT_LIST = Arrays.asList(V1_URL, V2_URL);

    @Autowired
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter(ObjectMapper objectMapper, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, AuthenticationManager manager) {
        UsernamePasswordAuthProcessingFilter filter = new UsernamePasswordAuthProcessingFilter(AUTH_URL, objectMapper, successHandler, failureHandler);
        filter.setAuthenticationManager(manager);

        return filter;
    }

    @Bean
    public JwtAuthProcessingFilter jwtAuthProcessingFilter(TokenExtractor tokenExtractor, AuthenticationFailureHandler failureHandler, AuthenticationManager authManager) {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(PERMIT_ENDPOINT_LIST, AUTHENTICATED_ENDPOINT_LIST);
        JwtAuthProcessingFilter filter = new JwtAuthProcessingFilter(matcher, tokenExtractor, failureHandler);
        filter.setAuthenticationManager(authManager);

        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper, JWTTokenFactory jwtTokenFactory) {
        return new UsernamePasswordAuthSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationFailureHandler usernamePasswordAuthFailureHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordAuthFailureHandler(objectMapper);
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthProvider).
        authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.
            requestMatchers(PERMIT_ENDPOINT_LIST.toArray(new String[0])).permitAll().
            requestMatchers(AUTH_URL).permitAll().requestMatchers(V1_URL, V2_URL).authenticated()).
            csrf(csrf -> csrf.disable()).
            sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
