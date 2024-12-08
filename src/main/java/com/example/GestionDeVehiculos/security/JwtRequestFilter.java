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
    private final static Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    @Autowired
    public JwtRequestFilter(UserDetailsServiceImpl usuariosDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = usuariosDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                email = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                // Manejo de excepción si el token no es válido o está malformado
                logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
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

