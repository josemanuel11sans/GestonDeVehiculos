package com.example.GestionDeVehiculos.Usuarios.control;

import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;


    public Usuarios crearUsuario(Usuarios usuario) {
        usuario.setFechaCreacion(Timestamp.from(Instant.now()));
        return usuariosRepository.save(usuario);
    }

    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios obtenerUsuarioPorId(Long id) {
        Optional<Usuarios> usuario = usuariosRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuarios actualizarUsuario(Long id, Usuarios usuario) {
        if (usuariosRepository.existsById(id)) {
            usuario.setId(id);
            return usuariosRepository.save(usuario);
        }
        return null;
    }

    public boolean eliminarUsuario(Long id) {
        if (usuariosRepository.existsById(id)) {
            Usuarios usuario = usuariosRepository.findById(id).orElse(null);
            if (usuario != null) {
                usuario.setStatus(false); // En lugar de eliminar, se desactiva.
                usuariosRepository.save(usuario);
                return true;
            }
        }
        return false;
    }
}
