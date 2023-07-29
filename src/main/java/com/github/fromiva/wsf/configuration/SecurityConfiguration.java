package com.github.fromiva.wsf.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security specific configuration class.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Produces a servlet security filter chain to support Spring Web MVC based
     * Spring Security filtering mechanism.
     * @param http security filter chain builder object
     * @return security filter chain
     * @throws Exception if an error occurred when building the filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/css/**", "/js/**").permitAll())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/images/**").fullyAuthenticated())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/**").fullyAuthenticated())
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /**
     * Produces a password encoder to support password encryption / decryption
     * Spring Security mechanism.
     * @return delegating password encoder with default mappings
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
