package com.example.GestionDeVehiculos.CategoriasDeServicios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categoriaServicios")
public class CategoriaDeServicios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "VARCHAR(120)")
    private String descripcion;
    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean status;

    public CategoriaDeServicios() {
    }
    //cgenerar de nuevo cuando se ponga la relacion con otra tabla si es nesesario
    public CategoriaDeServicios( String nombre, String descripcion, Boolean status) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.status = status;
    }

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
