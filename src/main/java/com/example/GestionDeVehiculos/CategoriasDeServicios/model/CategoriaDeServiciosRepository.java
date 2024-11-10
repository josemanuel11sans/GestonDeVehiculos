package com.example.GestionDeVehiculos.CategoriasDeServicios.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDeServiciosRepository extends JpaRepository<CategoriaDeServicios, Long> {
    //agregra las querys personalizadas nesesarias
}
