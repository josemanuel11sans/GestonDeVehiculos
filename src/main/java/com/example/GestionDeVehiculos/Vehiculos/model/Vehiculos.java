package com.example.GestionDeVehiculos.Vehiculos.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "vehiculos"
)
public class Vehiculos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "modelo", columnDefinition = "VARCHAR(50)")
    private String modelo;
    @Column(name = "marca", columnDefinition = "VARCHAR(50)")
    private String marca;
    @Column(name = "marca",columnDefinition = "VARCHAR(50)")
    private String color;

    //esta es la relacion con la tabla se servicio
    private String servicio;

    @Column(name = "status",columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;

    public Vehiculos() {
    }
    //generar de nuevo este constructor cuando se integre la relacion de otra tabla
    public Vehiculos(Long id, String modelo, String marca, String color, String servicio, Boolean status) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
        this.servicio = servicio;
        this.status = status;
    }

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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
