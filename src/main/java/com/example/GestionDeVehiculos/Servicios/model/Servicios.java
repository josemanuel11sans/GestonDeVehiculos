package com.example.GestionDeVehiculos.Servicios.model;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import jakarta.persistence.*;

@Entity
@Table(name="servicios")
public class Servicios {
    //declasaarcion de la entidad con
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "VARCHAR(100)")
    private String descripcion;
   //esta es la relacio con la tabla de categorias
    //private String categoria;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaDeServicios categoria;

    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;
    
    //aqui poner la relaccioens si son nenesarias

    public Servicios() {
    }

    //NOTA: si se agrega una relacion se tiene que generar de nuevo este cosnstructor

    public Servicios( String nombre, String descripcion, CategoriaDeServicios categoria, boolean status) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.status = status;
    }

    //getters y setters

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
