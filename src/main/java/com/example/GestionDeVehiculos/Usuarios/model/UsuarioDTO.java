package com.example.GestionDeVehiculos.Usuarios.model;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;




public class UsuarioDTO {
    @NotNull(groups = {CategoriaDeServiciosDTO.Modify.class, CategoriaDeServiciosDTO.ChangeStatus.class},message = "Es necesario el id")
    private Long id;

    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, CategoriaDeServiciosDTO.Register.class}, message = "Es necesario el nombre")
    private String nombre;

    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, Readable.class}, message = "son nesesarios los apellidos")
    private String apellidos;

    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, Readable.class}, message = "Es necesario el email")
    private String email;

    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, Readable.class}, message = "Es nesesario el telefono")
    private String telefono;

    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, Readable.class}, message = "Es necesaria la contraseña")
    private String contraseña;


    @NotNull(groups = { Register.class}, message = "El admin no puede ser nulo")
    private String admin;
//    @NotBlank(groups = {CategoriaDeServiciosDTO.Modify.class, Readable.class}, message = "son nesesarios los roles")
//    private Set<Role> roles;


    public UsuarioDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    //interfaces
    public interface Register {
    }

    public interface Modify {
    }

    public interface ChangeStatus {
    }
}
