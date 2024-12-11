package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculosService vehiculosService;

    @GetMapping("/all")
    public ResponseEntity<List<Vehiculo>> consultarVehiculos() {
        return ResponseEntity.ok(vehiculosService.consultarVehiculos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Object> registrarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        return vehiculosService.registrarVehiculo(vehiculoDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Object> actualizarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        return vehiculosService.actualizarVehiculo(vehiculoDTO);
    }

    @PutMapping("/status")
    public ResponseEntity<Object> changeStatus(@Validated @RequestBody VehiculoDTO dto) {
        return vehiculosService.cambiarStatus(dto);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Vehiculo>> consultarVehiculosActivos() {
        return ResponseEntity.ok(vehiculosService.consultarVehiculosActivos());
    }

    @PutMapping("/{vehiculoId}/asignar-servicio/{servicioId}")
    public ResponseEntity<String> asignarServicio(
            @PathVariable Long vehiculoId,
            @PathVariable Long servicioId) {
        vehiculosService.asignarServicio(vehiculoId, servicioId);
        return ResponseEntity.ok("Servicio asignado al vehículo correctamente.");
    }

    @PutMapping("/{vehiculoId}/remover-servicio/{servicioId}")
    public ResponseEntity<String> removerServicio(
            @PathVariable Long vehiculoId,
            @PathVariable Long servicioId) {
        vehiculosService.removerServicio(vehiculoId, servicioId);
        return ResponseEntity.ok("Servicio removido del vehículo correctamente.");
    }
}
