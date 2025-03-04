package com.daytona.cruddemo.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authentication.AuthenticationConverterServerWebExchangeMatcher;

@Configuration
public class DemoSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            return http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for Postman testing
                    .authorizeHttpRequests(configurer -> configurer
                            .requestMatchers(HttpMethod.POST, "/users/register","/users/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/employee").hasAnyRole("EMPLOYEE","ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.GET, "/employee/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                            .requestMatchers(HttpMethod.POST, "/employee").hasAnyRole("ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/employee").hasAnyRole("ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
           .build();
        }

        @Bean
    public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
            provider.setUserDetailsService(userDetailsService);
            return provider;
        }

        @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
            return config.getAuthenticationManager();
        }

    /*
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
*/
}
