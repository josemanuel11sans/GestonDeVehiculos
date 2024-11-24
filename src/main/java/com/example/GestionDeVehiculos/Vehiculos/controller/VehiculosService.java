package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculosService {

    @Autowired
    private VehiculoRepository vehiculosRepository;

    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        return vehiculosRepository.save(vehiculo);
    }

    public List<Vehiculo> consultarVehiculos() {
        return vehiculosRepository.findAll();
    }

    public List<Vehiculo> consultarVehiculosActivos() {
        return vehiculosRepository.findAll().stream()
                .filter(Vehiculo::isStatus)
                .toList();
    }

    public Vehiculo actualizarVehiculo(Long id, Vehiculo vehiculoActualizado) {
        Optional<Vehiculo> vehiculoExistente = vehiculosRepository.findById(id);
        if (vehiculoExistente.isPresent()) {
            Vehiculo vehiculo = vehiculoExistente.get();
            vehiculo.setModelo(vehiculoActualizado.getModelo());
            vehiculo.setMarca(vehiculoActualizado.getMarca());
            vehiculo.setColor(vehiculoActualizado.getColor());
            vehiculo.setStatus(vehiculoActualizado.isStatus());
            return vehiculosRepository.save(vehiculo);
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + id);
        }
    }

    public Vehiculo cambiarEstadoVehiculo(Long id, boolean estado) {
        Optional<Vehiculo> vehiculo = vehiculosRepository.findById(id);
        if (vehiculo.isPresent()) {
            Vehiculo v = vehiculo.get();
            v.setStatus(estado);
            return vehiculosRepository.save(v);
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + id);
        }
    }

    public Vehiculo asignarServicio(Long idVehiculo, Long idServicio) {
        return null;
    }

    public Vehiculo removerServicio(Long idVehiculo) {
        return null;
    }
}