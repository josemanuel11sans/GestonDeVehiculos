package com.example.GestionDeVehiculos.Vehiculos.model;

import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class VehiculoDTO {

    @NotNull(groups = {ChangeStatus.class}, message = "Es necesario el ID del vehículo")
    private Long id;

    private String modelo;
    private String marca;
    private String color;
    private boolean status;
    private Set<Servicios> servicios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Servicios> getServicios() {
        return servicios;
    }

    public void setServicios(Set<Servicios> servicios) {
        this.servicios = servicios;
    }

    // Interfaz de validación para cambio de estado
    public interface ChangeStatus {}
}
