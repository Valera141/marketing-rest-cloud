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
            // ✅ 1. PERMITIR ACCESO PÚBLICO A SWAGGER Y AL HOME
            .requestMatchers("/", "/doc/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
            
            // 2. REGLAS DE TU API (Esto se queda igual)
            .requestMatchers("/api/v1/campaigns/**").hasAnyRole("Admin", "Analyst")
            
            // 3. EL RESTO CERRADO
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults())
        .formLogin(withDefaults())
        
        // ... (el resto de tu manejo de excepciones sigue igual)
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
