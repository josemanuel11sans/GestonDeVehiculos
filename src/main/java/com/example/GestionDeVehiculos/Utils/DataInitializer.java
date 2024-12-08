package com.example.GestionDeVehiculos.Utils;

import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(UsuariosRepository userRepository, PasswordEncoder passwordEncoder ) {
        return args -> {
            //estos son los roles de preuba para admin y user
            if (!userRepository.findByEmail("user1@gmail.com").isPresent()) {
                Usuarios user = new Usuarios("user", "apellidosUser", "user1@gmail.com", "77774457253", passwordEncoder.encode("root"), "ROLE_USER", true);
                userRepository.saveAndFlush(user);
            }

// Verificar si no existe el segundo usuario con email "user2@gmail.com"
            if (!userRepository.findByEmail("user2@gmail.com").isPresent()) {
                Usuarios user = new Usuarios("user2", "apellidosUser2", "user2@gmail.com", "77774457253", passwordEncoder.encode("root"), "ROLE_ADMIN", true);
                userRepository.saveAndFlush(user);
            }
        };
    }
}
