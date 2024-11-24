package com.example.GestionDeVehiculos.Usuarios.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(40)", nullable = false)
    private String nombre;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(60)", nullable = false)
    private String apellidos;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", columnDefinition = "VARCHAR(15)")
    private String telefono;

    @Column(name = "contraseña", columnDefinition = "VARCHAR(256)", nullable = false)
    private String contraseña;

    @Column(name = "rol", columnDefinition = "ENUM('admin', 'usuario') DEFAULT 'usuario'")
    private String rol;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp fechaCreacion;

    public Usuarios() {
        this.fechaCreacion = Timestamp.from(Instant.now());
    }

    public Usuarios(Long id, String nombre, String apellidos, String email, String telefono, String contraseña, String rol, boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
        this.status = status;
        this.fechaCreacion = Timestamp.from(Instant.now());
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public java.sql.Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.sql.Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}