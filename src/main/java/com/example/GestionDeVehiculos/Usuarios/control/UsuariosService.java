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
        if (usuariosRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }
        if (usuariosRepository.existsByTelefono(usuario.getTelefono())) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado.");
        }
        usuario.setFechaCreacion(Timestamp.from(Instant.now()));
        return usuariosRepository.save(usuario);
    }

    public Usuarios actualizarUsuario(Long id, Usuarios usuario) {
        Usuarios usuarioExistente = usuariosRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Usuario no encontrado con el ID: " + id));

        // Validar correo electrónico duplicado
        if (!usuarioExistente.getEmail().equals(usuario.getEmail()) &&
                usuariosRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Validar teléfono duplicado
        if (!usuarioExistente.getTelefono().equals(usuario.getTelefono()) &&
                usuariosRepository.existsByTelefono(usuario.getTelefono())) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado.");
        }

        // Actualizar campos del usuario
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellidos(usuario.getApellidos());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setContraseña(usuario.getContraseña());
        usuarioExistente.setRoles(usuario.getRoles());
        usuarioExistente.setStatus(usuario.isStatus());

        return usuariosRepository.save(usuarioExistente);
    }

    public Usuarios obtenerUsuarioPorId(Long id) {
        return usuariosRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
    }

    public boolean eliminarUsuario(Long id) {
        Usuarios usuario = usuariosRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
        usuario.setStatus(false); // Cambiar estado en lugar de eliminar físicamente
        usuariosRepository.save(usuario);
        return true;
    }

    public List<Usuarios> obtenerTodosLosUsuarios() {
        return usuariosRepository.findAll();
    }
}

