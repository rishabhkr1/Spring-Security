package com.daytona.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

    /**
     *
     * @return
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}admin")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}admin")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}admin")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/employee").hasRole("EMPLOYEE")
                        .requestMatchers((HttpMethod.GET), "/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/employee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/employee/**").hasRole("ADMIN")
        );

        //use http basic authentication
        http.httpBasic(Customizer.withDefaults());
/**
 *      disable cross site request forgery(CSRF)
 *      in general not required for stateless REST APIs that use POST,PUT, DELETE AND PATCH
 */

        http.csrf(csrf->csrf.disable());
        return http.build();
    }
}
