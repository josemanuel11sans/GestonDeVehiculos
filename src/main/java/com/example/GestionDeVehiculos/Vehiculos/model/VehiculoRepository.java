package com.example.GestionDeVehiculos.Vehiculos.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    //Falta metodo para implementar los activos
    List<Vehiculo> findActiveVehicles();

}
