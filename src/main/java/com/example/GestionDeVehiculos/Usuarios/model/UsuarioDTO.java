package com.example.GestionDeVehiculos.Usuarios.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 40, message = "El nombre no puede exceder los 40 caracteres.")
    private String nombre;

    @NotBlank(message = "Los apellidos no pueden estar vacíos.")
    @Size(max = 60, message = "Los apellidos no pueden exceder los 60 caracteres.")
    private String apellidos;

    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Email(message = "El correo electrónico debe ser válido.")
    @Size(max = 50, message = "El correo electrónico no puede exceder los 50 caracteres.")
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "El teléfono debe contener entre 10 y 15 dígitos.")
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, max = 256, message = "La contraseña debe tener entre 8 y 256 caracteres.")
    private String contraseña;

    @NotNull(message = "El rol es obligatorio.")
    @Pattern(regexp = "^(admin|usuario)$", message = "El rol debe ser 'admin' o 'usuario'.")
    private String rol;

    private Boolean status;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nombre, String apellidos, String email, String telefono, String contraseña, String rol, Boolean status) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
