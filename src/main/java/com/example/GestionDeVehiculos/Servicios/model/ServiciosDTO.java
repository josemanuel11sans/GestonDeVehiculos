package com.example.GestionDeVehiculos.Servicios.model;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiciosDTO {
    @NotNull(groups = {ServiciosDTO.Modify.class, ServiciosDTO.ChangeStatus.class},message = "Es necesario el id")
    private Long id;
    @NotBlank(groups = {ServiciosDTO.Modify.class, ServiciosDTO.ChangeStatus.class},message = "Es necesario el nombre")
    private String nombre;
    @NotBlank(groups = {ServiciosDTO.Modify.class, ServiciosDTO.ChangeStatus.class},message = "Es necesaria la descricion")
    private String descripcion;
    @NotBlank(groups = {ServiciosDTO.Modify.class, ServiciosDTO.ChangeStatus.class},message = "Es necesaria la categoria")
    private CategoriaDeServicios categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaDeServicios getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDeServicios categoria) {
        this.categoria = categoria;
    }

    //interfaces
    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
