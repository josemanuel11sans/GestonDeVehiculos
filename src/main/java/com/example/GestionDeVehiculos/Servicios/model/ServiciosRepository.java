package com.example.GestionDeVehiculos.Servicios.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long> {
    //agrega las querys personalizadas y nesesarias
}
