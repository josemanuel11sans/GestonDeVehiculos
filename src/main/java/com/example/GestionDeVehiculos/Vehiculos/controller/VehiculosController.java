package com.example.GestionDeVehiculos.Vehiculos.controller;

import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.controller.VehiculosService;
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
    public ResponseEntity<Vehiculo> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculosService.registrarVehiculo(vehiculo));
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> consultarVehiculos() {
        return ResponseEntity.ok(vehiculosService.consultarVehiculos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Vehiculo>> consultarVehiculosActivos() {
        return ResponseEntity.ok(vehiculosService.consultarVehiculosActivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculosService.actualizarVehiculo(id, vehiculo));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Vehiculo> cambiarEstadoVehiculo(@PathVariable Long id, @RequestParam boolean estado) {
        return ResponseEntity.ok(vehiculosService.cambiarEstadoVehiculo(id, estado));
    }

    @PostMapping("/{idVehiculo}/asignar-servicio/{idServicio}")
    public ResponseEntity<String> asignarServicio(@PathVariable Long idVehiculo, @PathVariable Long idServicio) {
        return ResponseEntity.ok("Funcionalidad no implementada aún.");
    }

    @DeleteMapping("/{idVehiculo}/remover-servicio")
    public ResponseEntity<String> removerServicio(@PathVariable Long idVehiculo) {
        return ResponseEntity.ok("Funcionalidad no implementada aún.");
    }
}
