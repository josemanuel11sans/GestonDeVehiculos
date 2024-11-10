package com.example.GestionDeVehiculos.Vehiculos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehiculosRepository  extends JpaRepository<Vehiculos, Long> {

}
