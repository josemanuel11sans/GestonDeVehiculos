package com.example.GestionDeVehiculos.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir todos los orígenes
        configuration.addAllowedOrigin("*");

        // Permitir todos los métodos HTTP
        configuration.addAllowedMethod("*");

        // Permitir todos los encabezados
        configuration.addAllowedHeader("*");

        // Deshabilitar credenciales
        configuration.setAllowCredentials(false);

        // Aplicar la configuración a todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
