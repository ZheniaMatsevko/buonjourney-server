package org.naukma.buonjourneyserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/events/**", "/places/**", "/packing/**",
                                "/tickets/**", "/trips/**", "/items/**", "/users/**", "/signin"))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/events/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/events/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/places/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/places/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/places/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/places/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/packing/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/packing/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/packing/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/packing/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/tickets/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/tickets/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/tickets/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/tickets/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/trips/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/trips/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/trips/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/trips/**").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/items/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/items/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/items/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/items/**").authenticated()

                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


/*@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/events/**","/trips/**", "/items/**", "/users/**"))
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

}*/
