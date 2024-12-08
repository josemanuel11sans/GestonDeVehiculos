package com.example.GestionDeVehiculos.security;

//import com.example.GestionDeVehiculos.security.dto.AuthRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
////La clase AuthController es un controlador REST que maneja las peticiones de
//// autenticación para los usuarios de la aplicación.
//@RestController
//public class AuthController {
////    AuthenticationManager: El encargado de manejar el proceso de autenticación de los usuarios.
////    UserDetailsServiceImpl: Un servicio que proporciona detalles del usuario (como nombre de usuario, contraseñas, roles, etc.) basándose en el nombre de usuario.
////    JwtUtil: Utilidad para generar y validar JWT (tokens de autenticación).
//    private final AuthenticationManager authenticationManager;
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.jwtUtil = jwtUtil;
//    }
//    //Este método se encarga de manejar las solicitudes de autenticación en la ruta /login. Recibe un objeto AuthRequest,
//    // que probablemente contiene el nombre de usuario y la contraseña.
//    @PostMapping("/login")
//    public String login(@RequestBody AuthRequest authRequest) throws Exception {
//        try {
////            authenticationManager.authenticate(...): Este método intenta autenticar al usuario utilizando
////            las credenciales proporcionadas (nombre de usuario y contraseña).
////            Se crea un UsernamePasswordAuthenticationToken con el nombre de usuario y la contraseña
////            que el cliente envía en la solicitud.
////            Si las credenciales no son correctas, Spring Security lanzará una excepción BadCredentialsException.
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        } catch (BadCredentialsException e) {
////            Si ocurre una excepción BadCredentialsException (lo que indica que el nombre de usuario o la contraseña
////            son incorrectos), se lanza una nueva excepción con un mensaje que indica el error.
//            throw new Exception("Usuario o contraseña incorrectos", e);
//        }
////        Si la autenticación es exitosa, se obtiene un objeto UserDetails del servicio userDetailsService.
////        Este objeto contiene información sobre el usuario autenticado, como su nombre de usuario,
////        roles y contraseñas (aunque la contraseña ya no es necesaria después de la autenticación).
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
////        Una vez que el usuario es autenticado y sus detalles se han cargado, se utiliza la clase JwtUtil
////        para generar un JWT que representa al usuario autenticado.
////        jwtUtil.generateToken(userDetails) probablemente genera un token que contiene información como
////        el nombre de usuario y los roles del usuario, y este token puede ser usado para autenticar futuras
////        solicitudes del usuario.
//        final String jwt = jwtUtil.generateToken(userDetails);
////        Finalmente, el token JWT generado se devuelve al cliente. Este token se usará en futuras solicitudes
////        del cliente para autenticar al usuario.
//        return jwt;
//    }
//}
//
///*
//*
//* 4. Flujo de Trabajo Completo
//* El cliente hace una solicitud POST a /login, enviando el nombre de usuario y la contraseña en el cuerpo de la solicitud.
//* El AuthenticationManager intenta autenticar al usuario con las credenciales proporcionadas.
//* Si la autenticación es exitosa, UserDetailsServiceImpl carga los detalles del usuario.
//* El JwtUtil genera un JWT que representa al usuario autenticado.
//* El servidor devuelve el token JWT al cliente, quien puede usarlo en futuras solicitudes.
//* 5. Resumen General
//* Este controlador proporciona un punto de entrada para la autenticación de usuarios en la aplicación utilizando nombre de
//*  usuario y contraseña.
//* Utiliza Spring Security para autenticar las credenciales del usuario.
//* Si la autenticación es exitosa, se genera un token JWT que puede ser utilizado para la autenticación en futuras solicitudes.
//* Este enfoque es común en aplicaciones que usan autenticación sin estado (stateless), donde el servidor no mantiene
//* sesiones y la autenticación se maneja mediante un token en cada solicitud posterior.
//*
//* */

import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import com.example.GestionDeVehiculos.security.dto.AuthRequest;
import com.example.GestionDeVehiculos.security.dto.AuthResponse;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuariosRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, UsuariosRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getContraseña()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            Usuarios user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String jwt = jwtUtil.generateToken(userDetails);
            long expirationTime = jwtUtil.getExpirationTime();

            AuthResponse response = new AuthResponse(jwt, user.getId(), user.getEmail(), user.getAdmin(), expirationTime);
            return  new ResponseEntity<>(new Message(response, "Inicio de sesión exitoso", TypesResponse.SUCCESS), HttpStatus.OK);


        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new Message("Credenciales incorrectas", TypesResponse.ERROR), HttpStatus.UNAUTHORIZED);
        }
    }
}
