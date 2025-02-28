package com.daytona.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    @Autowired
//    private UserService userService;


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(configurer ->
//                configurer
//                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/employee").hasRole("EMPLOYEE")
//                        .requestMatchers((HttpMethod.GET), "/employee/**").hasRole("EMPLOYEE")
//                        .requestMatchers(HttpMethod.POST, "/employee").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.PUT, "/employee").hasRole("MANAGER")
//                        .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//        )
//    .httpBasic(Customizer.withDefaults()) // Enable Basic Auth
//                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for testing
//
//        return http.build();
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for Postman testing
                    .authorizeHttpRequests(configurer -> configurer
                            .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                            .requestMatchers(HttpMethod.GET, "/employee").hasAnyRole("EMPLOYEE","ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.GET, "/employee/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                            .requestMatchers(HttpMethod.POST, "/employee").hasAnyRole("ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.PUT, "/employee").hasAnyRole("ADMIN","MANAGER")
                            .requestMatchers(HttpMethod.DELETE, "/employee/**").hasRole("ADMIN")
                            .anyRequest().authenticated() //
                    )
                    .httpBasic(Customizer.withDefaults()); // Enable Basic Auth

            return http.build();
        }


    /**
     *      disable cross site request forgery(CSRF)
     *      in general not required for stateless REST APIs that use POST,PUT, DELETE AND PATCH
     */
//
//        http.
//                authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                //use http basic authentication
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable);
////                .userDetailsService(userService);
//        return http.build();
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
