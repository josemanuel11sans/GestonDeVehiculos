package com.example.GestionDeVehiculos.Usuarios.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    // Método para verificar si un email ya existe
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    //esta se usa en security
    Optional<Usuarios> findByEmail(String email);

    Optional<Usuarios> searchUsuariosByEmail(String email);
    Optional<Usuarios> searchAllById(Long id);
}