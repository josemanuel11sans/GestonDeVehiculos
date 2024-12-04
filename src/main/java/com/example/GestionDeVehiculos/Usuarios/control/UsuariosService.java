package com.example.GestionDeVehiculos.Usuarios.control;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuariosService {
    private UsuariosRepository usuariosRepository;

    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }
        //registar usuario
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> GuardarUsuario(UsuarioDTO dto){
        //tamaño del nombre
        if(dto.getNombre().length()<3){
            return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getNombre().length()>40) {
            return  new ResponseEntity<>(new Message("El nombre no puede se mayor a 50 caractres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //tamaño de los appellidos
        if(dto.getApellidos().length()<3){
            return  new ResponseEntity<>(new Message("Los apellidos no pueden tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getApellidos()   .length()>60) {
            return  new ResponseEntity<>(new Message("Los apellidos no pueden tener mas de 60 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //tamaño del email
        if(dto.getEmail().length()<5){
            return  new ResponseEntity<>(new Message("El email no puede tener menos de 5 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getEmail().length()>50) {
            return  new ResponseEntity<>(new Message("Email no pueden tener mas de 50 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //tamaño de el telefono
        if(dto.getTelefono().length()<10){
            return  new ResponseEntity<>(new Message("El telefono no puede ser menor a 10 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getTelefono().length()>13) {
            return  new ResponseEntity<>(new Message("El telefono no pueden tener mas de 13 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //tamaño de la contraseña
        if(dto.getContraseña().length()<4){
            return  new ResponseEntity<>(new Message("la contraseña no puede ser menor a 4 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getContraseña().length()>256) {
            return  new ResponseEntity<>(new Message("La contraseña no pueden tener mas de 256 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //validar si el email ya existe
        Optional<Usuarios> optionalUsuarios = usuariosRepository.searchUsuariosByEmail(dto.getEmail());
        if(optionalUsuarios.isPresent()){
            return new ResponseEntity<>(new Message("El Correo electronico ya existe",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
        dto.setApellidos(capitalizarPrimeraLetra(dto.getApellidos()));
        dto.setEmail(capitalizarPrimeraLetra(dto.getEmail()));
        dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
        dto.setTelefono(capitalizarPrimeraLetra(dto.getTelefono()));
        dto.setContraseña(encriptarContraseña(dto.getContraseña()));

        Usuarios usuario = new Usuarios(dto.getNombre(),dto.getApellidos(), dto.getEmail(),dto.getTelefono(), dto.getContraseña(), dto.getRoles(),true);
        usuario = usuariosRepository.saveAndFlush(usuario);
        if(usuario == null){
            return new ResponseEntity<>(new Message("No se registro el usuario", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(usuario, "Se registró el usuario", TypesResponse.SUCCESS), HttpStatus.OK);

    }
//   - Consultar usuarios

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(new Message(usuariosRepository.findAll(), "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }
//- Editar usuarios
//- Cambio de estado de usuarios (habilitar / deshabilitar)
//- Iniciar sesión
//- Cerrar sesión
//- Consultar perfil — Menos prioridad
//- Editar perfil — Menos prioridad
//- Cambio de contraseña - Perfil — Menos prioridad
//- Solicitud de cambio de contraseña — Menos prioridad
//- Cambio de contraseña por solicitud — Menos prioridad




    //funciones:

    //funcion poner primerra letra en mayuscula
    public static String capitalizarPrimeraLetra(String texto) {
        // Convierte toda la cadena a min&uacute;sculas.
        texto = texto.toLowerCase();
        // Convierte la primera letra a mayúsculas y la concatena con el resto.
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
    // Método para encriptar una contraseña
    public static String encriptarContraseña(String contraseña) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(contraseña);
    }
}

