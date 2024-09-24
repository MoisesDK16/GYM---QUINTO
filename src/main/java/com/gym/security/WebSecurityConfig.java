package com.gym.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        antMatcher(HttpMethod.GET, "/files/**")
                ).permitAll()
                .requestMatchers(
                        "/users/login",
                        "/users/register"
                ).permitAll()
                .requestMatchers(
                        "/api/categorias/**",
                        "/api/productos/**",
                        "/api/facturas/**",
                        "/api/clientes/**",
                        "/api/detalles/**",
                        "/apiEmail/**",
                        "/api/membresias/**",
                        "/personal/**",
                        "api/planes/**",
                        "/api/servicios/**",
                        "/users/**"
                ).hasAuthority("ADMIN")
                .requestMatchers(
                        "/api/categorias/me/**",
                        "/api/productos/me/**",
                        "/api/facturas/me/**",
                        "/api/clientes/me/**",
                        "/api/detalles/me/**",
                        "/apiEmail/me/**",
                        "/api/membresias/me/**",
                        "/personal/me/**",
                        "api/planes/me/**",
                        "/api/servicios/me/**"
                ).hasAuthority("CLIENTE")
                .anyRequest()
                .authenticated()
        ).exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.userDetailsService(userDetailsService);
        http.httpBasic(basic -> basic.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())));
        return http.build();
    }


}
