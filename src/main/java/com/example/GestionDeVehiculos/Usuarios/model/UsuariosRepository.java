package com.example.GestionDeVehiculos.Usuarios.model;

import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    // Query personalizada para buscar usuario por email
    Optional<Usuarios> findByEmail(String email);

    // Método para verificar si un email ya existe
    boolean existsByEmail(String email);
}
