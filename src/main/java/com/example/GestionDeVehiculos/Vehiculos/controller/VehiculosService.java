package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculosService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo registrarVehiculo(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = toEntity(vehiculoDTO);
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoDTO actualizarVehiculo(VehiculoDTO vehiculoDTO) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(vehiculoDTO.getId());
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setColor(vehiculoDTO.getColor());
            vehiculo.setStatus(vehiculoDTO.isStatus());
            vehiculo.setServicios(vehiculoDTO.getServicios());
            return toDTO(vehiculoRepository.save(vehiculo));
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoDTO.getId());
        }
    }

    public void cambiarEstadoVehiculo(Long id, boolean status) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(id);
        if (optionalVehiculo.isPresent()) {
            Vehiculo vehiculo = optionalVehiculo.get();
            vehiculo.setStatus(status);
            vehiculoRepository.save(vehiculo);
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + id);
        }
    }

    public List<Vehiculo> consultarVehiculosActivos() {
        return vehiculoRepository.findActiveVehicles();
    }

    private Vehiculo toEntity(VehiculoDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(dto.getId());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setColor(dto.getColor());
        vehiculo.setStatus(dto.isStatus());
        vehiculo.setServicios(dto.getServicios());
        return vehiculo;
    }

    private VehiculoDTO toDTO(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(vehiculo.getId());
        dto.setModelo(vehiculo.getModelo());
        dto.setMarca(vehiculo.getMarca());
        dto.setColor(vehiculo.getColor());
        dto.setStatus(vehiculo.isStatus());
        dto.setServicios(vehiculo.getServicios());
        return dto;
    }
}
