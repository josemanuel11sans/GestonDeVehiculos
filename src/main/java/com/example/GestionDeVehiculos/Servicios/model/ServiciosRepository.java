package com.example.GestionDeVehiculos.Servicios.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long> {
    //agrega las querys personalizadas y nesesarias
    Optional<Servicios> searchServiciosByNombre(String nombre);

    //consular activos
    List<Servicios> findAllByStatusOrderByNombre(boolean status);
}
