package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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

    public ResponseEntity<Object> registrarVehiculo(VehiculoDTO vehiculoDTO) {
        try {
            vehiculoDTO.validate();
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setColor(vehiculoDTO.getColor());
            vehiculo.setStatus(vehiculoDTO.isStatus());
            vehiculo = vehiculoRepository.save(vehiculo);
            return new ResponseEntity<>(new Message(vehiculo, "Vehículo registrado exitosamente", TypesResponse.SUCCESS), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage(), TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Vehiculo> obtenerTodosVehiculos() {
        return vehiculoRepository.findAll();
    }

    public ResponseEntity<Object> actualizarVehiculo(VehiculoDTO vehiculoDTO) {
        try {
            vehiculoDTO.validate();
            Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(vehiculoDTO.getId());
            if (optionalVehiculo.isPresent()) {
                Vehiculo vehiculo = optionalVehiculo.get();
                vehiculo.setModelo(vehiculoDTO.getModelo());
                vehiculo.setMarca(vehiculoDTO.getMarca());
                vehiculo.setColor(vehiculoDTO.getColor());
                vehiculo.setStatus(vehiculoDTO.isStatus());
                vehiculo = vehiculoRepository.save(vehiculo);
                return new ResponseEntity<>(new Message(toDTO(vehiculo), "Vehículo actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Message("Vehículo no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new Message(e.getMessage(), TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Vehiculo> consultarVehiculosActivos() {
        return vehiculoRepository.findAll().stream()
                .filter(Vehiculo::isStatus)
                .toList();
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> cambiarStatus(VehiculoDTO dto) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(dto.getId());
        if (!optionalVehiculo.isPresent()) {
            return new ResponseEntity<>(new Message("No se encontró el vehículo", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Vehiculo vehiculo = optionalVehiculo.get();
        vehiculo.setStatus(!vehiculo.isStatus());
        vehiculo = vehiculoRepository.saveAndFlush(vehiculo);

        return new ResponseEntity<>(new Message(vehiculo, "Estado del vehículo modificado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
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
