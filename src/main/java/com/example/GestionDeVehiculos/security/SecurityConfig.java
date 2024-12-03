package com.example.GestionDeVehiculos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;//
import org.springframework.context.annotation.Configuration;//
import org.springframework.security.authentication.AuthenticationManager;
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
    /*
    * Se inyecta un filtro personalizado JwtRequestFilter que probablemente se encargue de verificar y validar los
    * JWT en cada solicitud.
    * Este filtro se utilizará más tarde para interceptar las peticiones HTTP antes de que
    * lleguen al filtro de autenticación estándar (UsernamePasswordAuthenticationFilter).
    * */
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
        * http.csrf(AbstractHttpConfigurer::disable): Deshabilita la protección CSRF
        * (usualmente necesaria para aplicaciones que no son RESTful).
        * En aplicaciones RESTful que utilizan JWT, no es necesario CSRF.
        * */
        http.csrf(AbstractHttpConfigurer::disable)
                //authorizeHttpRequests(auth -> auth...): Configura las reglas de autorización para las rutas:
                .authorizeHttpRequests(auth -> auth
                        //requestMatchers("/login", "/register").permitAll():
                        // Permite el acceso a las rutas /login y /register sin requerir autenticación.
                        .requestMatchers("/login").permitAll()
                        //requestMatchers("/town/**").hasAuthority("ROLE_TOWN_ACCESS"):
                        // Las rutas que empiezan con /town/ solo pueden ser accesibles
                        // por usuarios con el rol ROLE_TOWN_ACCESS.
                        .requestMatchers("/servicios/**").hasAnyAuthority("SIMPLE","ADMIN")
                        //requestMatchers("/state/**").hasAuthority("ROLE_STATE_ACCESS"):
                        // Las rutas que empiezan con /state/ solo pueden ser accesibles
                        // por usuarios con el rol ROLE_STATE_ACCESS.
                        .requestMatchers("/usuarios/**").hasAuthority("ADMIN")
                        .requestMatchers("/vehiculos/**").hasAuthority("ADMIN")
                        .requestMatchers("/CategoriasDeServicios/**").hasAuthority("ADMIN")
                        //anyRequest().authenticated():
                        // Requiere autenticación para cualquier otra ruta que no se haya
                        // especificado previamente.
                        .anyRequest().authenticated()
                )
                /*
                * sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)):
                * Configura que la aplicación no debe mantener ninguna sesión (sin estado).
                * Esto es común en aplicaciones que utilizan JWT, ya que la autenticación se maneja a través del token
                * en cada solicitud.
                * */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class):
                // Añade el filtro JwtRequestFilter antes del filtro estándar de autenticación de
                // Spring (UsernamePasswordAuthenticationFilter). Este filtro probablemente se encarga
                // de leer el token JWT de las solicitudes entrantes, validar el token y, si es
                // válido, establecer el contexto de autenticación.
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
    /*
    * Este método devuelve un AuthenticationManager, que se utiliza para manejar la autenticación de
    * usuarios (por ejemplo, autenticación basada en nombre de usuario y contraseña).
    * Utiliza la configuración de autenticación proporcionada por Spring Security a
    * través de AuthenticationConfiguration.
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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