package com.example.GestionDeVehiculos.CategoriasDeServicios.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class CategoriaDeServiciosDTO {
    @NotNull(groups = {Modify.class, ChangeStatus.class},message = "Es necesario el id")
    private Long id;

    @NotBlank(groups = {Modify.class, Register.class}, message = "Es necesario el nombre")
    private String nombre;

    @NotBlank(groups = {Modify.class, Readable.class}, message = "Es necesaria lal descripcion")
    private String descripcion;

    //constructor
    public CategoriaDeServiciosDTO() {
    }

    //gueteers y setters
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

    //interfaces
    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
