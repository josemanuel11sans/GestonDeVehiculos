package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculosService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ServiciosRepository serviciosRepository;

    public List<Vehiculo> consultarVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo registrarVehiculo(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setStatus(vehiculoDTO.isStatus());
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
            return toDTO(vehiculoRepository.save(vehiculo));
        } else {
            throw new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoDTO.getId());
        }
    }
    public List<Vehiculo> consultarVehiculosActivos() {
        return vehiculoRepository.findAll().stream()
                .filter(Vehiculo::isStatus)
                .toList();
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

    public void asignarServicio(Long vehiculoId, Long servicioId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));
        Servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + servicioId));

        vehiculo.getServicios().add(servicio);
        vehiculoRepository.save(vehiculo);
    }

    public void removerServicio(Long vehiculoId, Long servicioId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));
        Servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + servicioId));

        vehiculo.getServicios().remove(servicio);
        vehiculoRepository.save(vehiculo);
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
