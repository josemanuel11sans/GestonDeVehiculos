package com.example.GestionDeVehiculos.Usuarios.model;

import com.example.GestionDeVehiculos.Role.model.Role;
import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", columnDefinition = "VARCHAR(40)")
    private String nombre;

    @Column(name = "apellidos", columnDefinition = "VARCHAR(60)")
    private String apellidos;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", unique = true)
    private String email;

    @Column(name = "telefono", columnDefinition = "VARCHAR(13)")
    private String telefono;

    @Column(name = "contraseña", columnDefinition = "VARCHAR(256)", nullable = false)
    private String contraseña;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp fechaCreacion;

    public Usuarios() {
        this.fechaCreacion = Timestamp.from(Instant.now());
    }

    public Usuarios(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public Usuarios( String nombre, String apellidos, String email, String telefono, String contraseña, Set<Role> roles) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.roles = roles;
        this.status = true;
        this.fechaCreacion  = Timestamp.from(Instant.now());
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
    //cambio de roles
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    //termina cambios de roles
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public java.sql.Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(java.sql.Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}