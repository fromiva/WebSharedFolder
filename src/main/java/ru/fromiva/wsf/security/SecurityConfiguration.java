package ru.fromiva.wsf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/css/**", "/js/**", "/images/favicon.ico").permitAll())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/users/login", "/users/signup").permitAll())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/install").permitAll())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/images/**").authenticated())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/admin", "/admin/**")
                                .hasAnyRole("ROOT_ADMIN", "ADMIN"))
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/actuator", "/actuator/**")
                                .hasAnyRole("ROOT_ADMIN", "ADMIN"))
                .authorizeHttpRequests(req ->
                        req.anyRequest().authenticated())
                .formLogin(req -> req
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/login?error")
                        .usernameParameter("email")
                        .passwordParameter("password"))
                .logout(req -> req
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .build();
    }

    /**
     * Produces a password encoder to support password encryption / decryption
     * Spring Security mechanism.
     * @return delegating password encoder with default mappings
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
