package com.example.marketing.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // <--- IMPORTANTE AÑADIR ESTO
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.marketing.exception.CustomAccessDeniedHandler;
import com.example.marketing.exception.CustomAuthenticationEntryPoint;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))

        .authorizeHttpRequests(auth -> auth
            // 1. REGLAS ESPECÍFICAS (Opcional: solo si quieres distinguir roles)
            .requestMatchers("/api/v1/campaigns/**").hasAnyRole("Admin", "Analyst")
            
            // 2. REGLA DE ORO: TODO LO DEMÁS REQUIERE LOGIN
            // Esto cubre Swagger, la raíz "/", y cualquier otra cosa.
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .formLogin(withDefaults()) // Habilita el formulario de login visual

        .logout(logout -> logout
            .logoutUrl("/logout")             // 1. La dirección para cerrar sesión
            .logoutSuccessUrl("/login?logout") // 2. A dónde te manda al salir (al login)
            .invalidateHttpSession(true)      // 3. Borra la sesión del servidor
            .deleteCookies("JSESSIONID")      // 4. Borra la cookie de memoria
            .permitAll()                      // 5. Deja que cualquiera pueda cerrar sesión
        )

        
        .exceptionHandling(exception -> exception
            .defaultAuthenticationEntryPointFor(authenticationEntryPoint, new AntPathRequestMatcher("/api/**"))
            .accessDeniedHandler(accessDeniedHandler)
        );

    return http.build();
}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); 
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


