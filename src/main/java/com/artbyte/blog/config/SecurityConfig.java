package com.artbyte.blog.config;

import com.artbyte.blog.filter.JwtRequestFilter;
import com.artbyte.blog.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final JwtRequestFilter jwtRequestFilter;

    // Configuración de las reglas de seguridad para las rutas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desactiva CSRF ya que se usará JWT
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)  // Añade el filtro JWT antes del filtro de autenticación
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/authenticate").permitAll()  // Permite acceso sin autenticación a estas rutas
                        .requestMatchers("/api/blog/**",
                                "/api/comments/**",
                                "/api/users/**").authenticated()  // Requiere autenticación para blogs y comentarios
                        .anyRequest().authenticated()  // Requiere autenticación para cualquier otra ruta
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Configura la aplicación para que no use sesiones HTTP
                );
        return http.build();
    }

    // Configuración de CORS
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");  // Permite todas las solicitudes de cualquier origen
        config.addAllowedHeader("*");  // Permite todos los encabezados
        config.addAllowedMethod("*");  // Permite todos los métodos HTTP
        source.registerCorsConfiguration("/**", config);  // Aplica la configuración CORS a todas las rutas
        return new CorsFilter(source);
    }

    // Configuración de autenticación con DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);  // Usa el servicio de detalles de usuario personalizado
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());  // Usa el codificador de contraseñas BCrypt
        return daoAuthenticationProvider;
    }

    // Bean para el PasswordEncoder con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Codificador de contraseñas seguro
    }

    // Bean para el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // Obtiene el AuthenticationManager del contexto de configuración
    }
}
