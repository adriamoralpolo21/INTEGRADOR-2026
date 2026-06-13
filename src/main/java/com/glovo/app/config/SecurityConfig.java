package com.glovo.app.config;

import com.glovo.app.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        // 🔹 Para poder usar AuthenticationManager en el AuthApiController
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // 1) Seguridad para API (JWT, stateless)
        @Bean
        @Order(1)
        public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
                http
                                // 👇 SOLO se aplica a /api/auth/**
                                .securityMatcher("/api/auth/**")
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // públicos en la API de auth
                                                .requestMatchers("/api/auth/login", "/api/auth/refresh").permitAll()
                                                // cualquier otra ruta bajo /api/auth/** autenticada (si tuvieses más)
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // 2) Seguridad para WEB (lo que ya tenías)
       @Bean
@Order(2)
public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                    "/", "/login", "/register",
                    "/soporte", "/soporte/enviar",
                    "/ofrecemos", "/comida", "/tiendas", "/farmacia",
                    "/supermercado",
                    "/cart/**",
                    "/css/**", "/js/**", "/images/**", "/imgs/**",
                    "/webjars/**",
                    "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"
            ).permitAll()

            // 🔥 Estas rutas solo para usuarios logueados:
            .requestMatchers(
                    "/checkout",
                    "/orden/**",
                    "/mis-pedidos",
                    "/mis-pedidos/**",
                    "/api/ubicacion"      // 👈 NUEVO: guardar dirección solo logueado
            ).authenticated()

            // 🔥 Rutas del REPARTIDOR
            .requestMatchers("/repartidor/**").hasRole("REPARTIDOR")

            // 🔥 Panel admin con rol ADMIN
            .requestMatchers("/admin/**").hasRole("ADMIN")

            // ❗ Cualquier otra ruta, requiere sesión
            .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.ignoringRequestMatchers(
                new AntPathRequestMatcher("/register"),
                new AntPathRequestMatcher("/cart/**"),
                new AntPathRequestMatcher("/api/ubicacion") // 👈 NUEVO: sin CSRF en este endpoint
        ))
        .formLogin(form -> form
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true))
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll());

    return http.build();
}


        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
