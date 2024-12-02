package com.example.GestionDeVehiculos.security;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

//La clase JwtRequestFilter extiende OncePerRequestFilter de Spring, lo que significa que este filtro se ejecutará para cada solicitud, pero solo una vez.
//La anotación @Component marca esta clase como un componente de Spring, por lo que será detectado y administrado por el contenedor de Spring.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    //logger: Para registrar los errores y actividades dentro del filtro.
    private final static Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    //userDetailsService: Un servicio que carga detalles de usuario, probablemente implementado para interactuar con la base de datos.
    private final UserDetailsServiceImpl userDetailsService;
    //jwtUtil: Una instancia del utilitario de JWT (JwtUtil) para extraer y validar el token
    private final JwtUtil jwtUtil;
    //El constructor recibe las dependencias necesarias para trabajar con el filtro.
    // Spring inyecta las instancias de UserDetailsServiceImpl y JwtUtil a través de la anotación @Autowired.
    @Autowired
    public JwtRequestFilter(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    //Este es el corazón del filtro, donde se realizan las verificaciones del token JWT
    //y se configura la autenticación en el contexto de seguridad si el token es válido.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtención del encabezado de autorización: El filtro comienza por leer el encabezado Authorization de la solicitud HTTP.
        final String authorizationHeader = request.getHeader("Authorization");


        //        Verificación del formato del token: Si el encabezado comienza con "Bearer ", se extrae el JWT (eliminar la palabra "Bearer ").
        //        Extracción del nombre de usuario: Luego, el nombre de usuario se extrae del token utilizando jwtUtil.extractUsername(jwt).
        //        Si el token es inválido o está malformado, se captura la excepción y se registra un error
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Manejo de excepción si el token no es válido o está malformado
                logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage());
            }
        }

        // Validar el token y autenticar al usuario si el contexto de seguridad está vacío

//        Validación del token:
//        Si se extrajo el nombre de usuario y no hay una autenticación ya presente en el contexto de seguridad, el filtro intenta autenticar al usuario.
//        Utiliza userDetailsService.loadUserByUsername(username) para cargar los detalles del usuario.
//        Luego, valida el token con jwtUtil.validateToken(jwt, userDetails).
//        Autenticación del usuario:
//        Si el token es válido, crea un UsernamePasswordAuthenticationToken con los detalles del usuario y sus autoridades.
//        Asocia la autenticación al contexto de seguridad usando SecurityContextHolder.getContext().setAuthentication().
//        También agrega detalles adicionales de autenticación con WebAuthenticationDetailsSource (como la IP y la URL de la solicitud).
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //Después de hacer las verificaciones y establecer la autenticación (si es válida),
        // filtro pasa la solicitud al siguiente filtro en la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}

/**
 *Resumen:
 * Este filtro se encarga de:
 *
 * Verificar si existe un token JWT en el encabezado de autorización.
 * Extraer el nombre de usuario y validar el token.
 * Autenticar al usuario en el contexto de seguridad de Spring si el token es válido.
 * Registrar errores si algo sale mal, como un token inválido o malformado.
 * El JwtRequestFilter es esencial para asegurar que solo los usuarios con un token JWT válido puedan acceder a los recursos protegidos de la aplicación.
 */

