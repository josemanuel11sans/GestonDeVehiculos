package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculosController {

    @Autowired
    private VehiculosService vehiculosService;

    @PostMapping("/registrar")
    public ResponseEntity<Vehiculo> registrarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        return ResponseEntity.ok(vehiculosService.registrarVehiculo(vehiculoDTO));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<VehiculoDTO> actualizarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        return ResponseEntity.ok(vehiculosService.actualizarVehiculo(vehiculoDTO));
    }

    @PutMapping("/cambiar-estado/{id}")
    public ResponseEntity<String> cambiarEstadoVehiculo(@PathVariable Long id, @RequestParam boolean status) {
        vehiculosService.cambiarEstadoVehiculo(id, status);
        return ResponseEntity.ok("Estado del vehículo actualizado correctamente.");
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
