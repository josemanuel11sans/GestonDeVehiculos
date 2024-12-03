//package com.example.GestionDeVehiculos.Utils;
//import com.example.GestionDeVehiculos.Role.model.Role;
//import com.example.GestionDeVehiculos.Role.model.RoleRepository;
//import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
//import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
///**
// * La clase está marcada con la anotación @Configuration, lo que significa que es una clase
// * de configuración de Spring. El objetivo de esta clase es inicializar datos predeterminados
// * en la base de datos cuando la aplicación se arranca.
// *
// */
//
///*
//* Este método define un CommandLineRunner como un bean. Un CommandLineRunner es un componente de
//*  Spring Boot que se ejecuta al inicio de la aplicación y permite realizar tareas de inicialización.
//* Se inyectan tres dependencias:
//* userRepository: Repositorio para acceder a la entidad User.
//* roleRepository: Repositorio para acceder a la entidad Role.
//* passwordEncoder: Se usa para cifrar las contraseñas antes de almacenarlas.
//* */
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    CommandLineRunner initDatabase(UsuariosRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            /*
//            *  El propósito de este método es comprobar si los roles "ROLE_TOWN_ACCESS" y
//            *  "ROLE_STATE_ACCESS" existen en la base de datos. Si no existen, los crea.
//            * Posteriormente, crea usuarios asociados a esos roles.
//            * */
//
////            Se busca si ya existe un rol con el nombre "ROLE_TOWN_ACCESS".
////            Si no se encuentra (optionalRole.isPresent() devuelve false),
////            se crea un nuevo rol con ese nombre y se guarda en la base de
////            datos usando roleRepository.saveAndFlush(roleTown).
//            Optional<Role> optionalRole = roleRepository.findByName("ROLE_TOWN_ACCESS");
//            if (!optionalRole.isPresent()) {
//                Role roleTown = new Role("ROLE_TOWN_ACCESS");
//                roleRepository.saveAndFlush(roleTown);
//                //Luego, se busca si ya existe un usuario con el nombre de usuario "townUser".
//                // Si no existe, se crea un nuevo usuario con ese nombre de usuario y la contraseña "password123", que se cifra usando el passwordEncoder.encode().
//                //Se asigna el rol ROLE_TOWN_ACCESS al usuario recién creado.
//                //Finalmente, se guarda el usuario en la base de datos utilizando userRepository.saveAndFlush(userTown).
//                Optional<Usuarios> optionalUser = userRepository.findByEmail("townUser");
//                if (!optionalUser.isPresent()) {
//                    Usuarios userTown = new Usuarios("townUser", passwordEncoder.encode("password123"));
//                    userTown.getRoles().add(roleTown);
//                    userRepository.saveAndFlush(userTown);
//                }
//            }
//            // Creación del Rol "ROLE_STATE_ACCESS": Se realiza el mismo proceso para el rol "ROLE_STATE_ACCESS".
//            // Primero, se verifica si ya existe el rol, y si no es así, se crea y se guarda en la base de datos.
//            // Luego, se busca si existe un usuario con el nombre de usuario "stateUser". Si no existe, se crea un
//            // nuevo usuario con el nombre de usuario "stateUser", se le asigna el rol "ROLE_STATE_ACCESS" y se
//            // guarda en la base de datos.
//            optionalRole = roleRepository.findByName("ROLE_STATE_ACCESS");
//            if (!optionalRole.isPresent()) {
//                Role roleState = new Role("ROLE_STATE_ACCESS");
//                roleRepository.saveAndFlush(roleState);
//
//            Optional<Usuarios> optionalUser = userRepository.findByEmail("stateUser");
//                if (!optionalUser.isPresent()) {
//                    Usuarios userState = new Usuarios("stateUser", passwordEncoder.encode("password123"));
//                    userState.getRoles().add(roleState);
//                    userRepository.saveAndFlush(userState);
//                }
//            }
//
//        };
//    }
//}
///*
//*  Flujo Completo del Método initDatabase
//Verificar si el rol "ROLE_TOWN_ACCESS" existe:
//Si no existe, se crea y se guarda.
//Luego se verifica si el usuario "townUser" existe:
//Si no existe, se crea el usuario, se le asigna el rol "ROLE_TOWN_ACCESS" y se guarda en la base de datos.
//Verificar si el rol "ROLE_STATE_ACCESS" existe:
//Si no existe, se crea y se guarda.
//Luego se verifica si el usuario "stateUser" existe:
//Si no existe, se crea el usuario, se le asigna el rol "ROLE_STATE_ACCESS" y se guarda en la base de datos.
//* */
//
///*
//* Resumen General
//* Este código garantiza que, al iniciar la aplicación, si los roles "ROLE_TOWN_ACCESS" y "ROLE_STATE_ACCESS"
//* no existen en la base de datos, se crearán automáticamente. Además, si los usuarios "townUser" y "stateUser"
//* no existen, también serán creados y asignados a los roles correspondientes.
//* Este enfoque es común en aplicaciones que requieren una inicialización básica de datos, como la creación de
//* roles y usuarios predeterminados, para que los usuarios puedan acceder a ciertas áreas de la aplicación con permisos específicos
//* */
