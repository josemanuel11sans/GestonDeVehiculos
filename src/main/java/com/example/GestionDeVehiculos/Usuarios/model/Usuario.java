package com.example.GestionDeVehiculos.Usuarios.model;

import jakarta.persistence.*;


@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", columnDefinition = "VARCHAR(50)")
    private String nombre;
    @Column(name="apellidos", columnDefinition = "VARCHAR(100)")
    private String apellidos;
    @Column(name="correoElectronico", columnDefinition = "VARCHAR(60)")
    private String correoElectronico;
    @Column(name="telefono", columnDefinition = "INTEGER(11)")
    private int telefono;
    //no usar n para contraseña
    @Column(name="contrasena", columnDefinition = "VARCHAR(50)")
    private String contrasena;
   //esat puede ser una tabla
    @Column(name = "rol", columnDefinition = "INTERGER(2)")
    private int rol;
    //el estatus en automatico se guarda como TRUE al guardar
    @Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
    private Boolean Status;

    public Usuario() {
    }
   //construir de neuvo cuando se meta las relalciones
    public Usuario(Long id, String nombre, String apellidos, String correoElectronico, int telefono, String contrasena, int rol, Boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        Status = status;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
}
