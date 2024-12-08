package com.example.GestionDeVehiculos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;//
import org.springframework.context.annotation.Configuration;//
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//Esta clase configura la seguridad de la aplicación utilizando Spring Security.
// Está marcada con la anotación @Configuration, lo que indica que esta clase es una configuración de Spring.
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        //rutas publicas
                        .requestMatchers("/login").permitAll()

                        .requestMatchers("/servicios/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/vehiculos/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/CategoriasDeServicios/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    //Este método define un PasswordEncoder que utiliza el algoritmo BCrypt para codificar las contraseñas.
    //BCrypt es un algoritmo de cifrado de contraseñas fuerte y seguro que es ampliamente utilizado.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    Resumen General
//    Autenticación sin estado: La configuración desactiva la gestión de sesiones y utiliza JWT para la autenticación, lo que significa que no se almacenan sesiones en el servidor.
//    Rutas protegidas: Se definen rutas públicas (/login, /register) y rutas protegidas que requieren roles específicos (ROLE_TOWN_ACCESS, ROLE_STATE_ACCESS).
//    Filtro JWT: Se configura un filtro personalizado (JwtRequestFilter) para procesar el token JWT antes de que se realice la autenticación.
//    Cifrado de contraseñas: Se utiliza BCryptPasswordEncoder para cifrar las contraseñas de los usuarios.
}