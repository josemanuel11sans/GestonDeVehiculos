package com.example.GestionDeVehiculos.Role.model;

import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonManagedReference
    private Set<Usuarios> users = new HashSet<>();

    public Role(String roleStateAccess) {}

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Usuarios> getUsers() {
        return users;
    }

    public void setUsers(Set<Usuarios> users) {
        this.users = users;
    }
}
