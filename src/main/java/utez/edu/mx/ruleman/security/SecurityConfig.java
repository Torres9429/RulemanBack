package utez.edu.mx.ruleman.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors() // Habilita CORS usando el CorsConfigurationSource definido
                .and()
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(//todos
                                "/auth/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/").permitAll() // Para el registro de clientes
                        .requestMatchers(HttpMethod.POST, "/api/vehiculos/").permitAll() // Para registrar vehiculos de clientes
                        .requestMatchers( //ADMIN
                                //usuarios
                                "auth/login",
                                "api/usuarios/**",
                                "api/servicios/**",
                                "api/piezas/**",
                                "api/tiposervicio/**"
                        ).hasAnyAuthority("ADMIN")
                        .requestMatchers( //MECANICO
                                "api/servicios/**"

                        ).hasAnyAuthority("MECANICO")
                        .requestMatchers( //CLIENTE
                                //vehiculos
                                "api/vehiculos/**"

                        ).hasAnyAuthority("CLIENTE")

                        .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Especifica el origen permitido explícitamente, en lugar de "*"
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Permite exponer ciertos headers
        configuration.setAllowCredentials(false); // Permite el envío de credenciales como cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
