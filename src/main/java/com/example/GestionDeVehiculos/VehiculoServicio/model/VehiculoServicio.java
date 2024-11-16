package com.example.GestionDeVehiculos.VehiculoServicio.model;

import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculos;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VehiculosServicios")
public class VehiculoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculos vehiculo;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicios servicio;

    private LocalDateTime asignadoAt;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado { ACTIVO, COMPLETADO, CANCELADO }

    // Getters and setters...


    public VehiculoServicio() {
    }

    public VehiculoServicio(Integer id, Vehiculos vehiculo, Servicios servicio, LocalDateTime asignadoAt, Estado estado) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.servicio = servicio;
        this.asignadoAt = asignadoAt;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }

    public LocalDateTime getAsignadoAt() {
        return asignadoAt;
    }

    public void setAsignadoAt(LocalDateTime asignadoAt) {
        this.asignadoAt = asignadoAt;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
