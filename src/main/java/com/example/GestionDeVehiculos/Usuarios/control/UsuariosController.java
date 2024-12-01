package com.example.GestionDeVehiculos.Usuarios.control;

import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public ResponseEntity<Object> obtenerUsuarios() {
        List<Usuarios> usuarios = usuariosService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(Map.of("type", "SUCCESS", "result", usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuarios usuario = usuariosService.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> crearUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Usuarios usuario = convertirDTOaEntidad(usuarioDTO);
            Usuarios nuevoUsuario = usuariosService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Usuarios usuario = convertirDTOaEntidad(usuarioDTO);
            Usuarios usuarioActualizado = usuariosService.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            usuariosService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private Usuarios convertirDTOaEntidad(UsuarioDTO usuarioDTO) {
        return new Usuarios(
                usuarioDTO.getId(),
                usuarioDTO.getNombre(),
                usuarioDTO.getApellidos(),
                usuarioDTO.getEmail(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getContraseña(),
                usuarioDTO.getRol(),
                usuarioDTO.getStatus() != null ? usuarioDTO.getStatus() : true
        );
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
