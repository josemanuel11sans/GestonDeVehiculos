package com.example.GestionDeVehiculos.Vehiculos.model;

import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class VehiculoDTO {

    private Long id;

    @NotBlank(message = "El modelo no puede estar vacío")
    @Size(max = 100, message = "El modelo no puede exceder los 100 caracteres")
    private String modelo;

    @NotBlank(message = "La marca no puede estar vacía")
    @Size(max = 100, message = "La marca no puede exceder los 100 caracteres")
    private String marca;

    @NotBlank(message = "El color no puede estar vacío")
    @Size(max = 50, message = "El color no puede exceder los 50 caracteres")
    private String color;

    private boolean status = true;

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
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("El modelo no puede estar vacío", TypesResponse.WARNING).toString());
        }
        if (modelo.length() > 100) {
            throw new IllegalArgumentException(
                    new Message("El modelo excede los 100 caracteres permitidos", TypesResponse.WARNING).toString());
        }
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("La marca no puede estar vacía", TypesResponse.WARNING).toString());
        }
        if (marca.length() > 100) {
            throw new IllegalArgumentException(
                    new Message("La marca excede los 100 caracteres permitidos", TypesResponse.WARNING).toString());
        }
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("El color no puede estar vacío", TypesResponse.WARNING).toString());
        }
        if (color.length() > 50) {
            throw new IllegalArgumentException(
                    new Message("El color excede los 50 caracteres permitidos", TypesResponse.WARNING).toString());
        }
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


    public void validate() {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("El modelo no puede estar vacío", TypesResponse.WARNING).toString());
        }
        if (modelo.length() > 100) {
            throw new IllegalArgumentException(
                    new Message("El modelo excede los 100 caracteres permitidos", TypesResponse.WARNING).toString());
        }
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("La marca no puede estar vacía", TypesResponse.WARNING).toString());
        }
        if (marca.length() > 100) {
            throw new IllegalArgumentException(
                    new Message("La marca excede los 100 caracteres permitidos", TypesResponse.WARNING).toString());
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException(
                    new Message("El color no puede estar vacío", TypesResponse.WARNING).toString());
        }
        if (color.length() > 50) {
            throw new IllegalArgumentException(
                    new Message("El color excede los 50 caracteres permitidos", TypesResponse.WARNING).toString());
        }
    }

}
