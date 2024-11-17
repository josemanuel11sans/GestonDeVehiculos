package com.example.GestionDeVehiculos.CategoriasDeServicios.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaDeServiciosRepository extends JpaRepository<CategoriaDeServicios, Long> {
    //agregra las querys personalizadas nesesarias

    Optional<CategoriaDeServicios> searchCategoriaDeServiciosByNombre(String nombre);
    //Optional<CategoriaDeServicios> searchCategoriaDeServiciosByStatus(boolean status);
    List<CategoriaDeServicios> findAllByStatusOrderByNombre(boolean status);
}
