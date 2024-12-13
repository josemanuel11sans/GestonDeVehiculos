package com.example.GestionDeVehiculos.Usuarios.control;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuariosService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    private UsuariosRepository usuariosRepository;

    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }





    //   - registar usuario
            //Ejemplo
            //    {
            //        "nombre": "user5",
            //            "apellidos": "apellidosUser5",
            //            "email": "user5@gmail.com",
            //            "telefono": "77774457253",
            //            "contraseña": "root",
            //            "admin":"ROLE_USER" //usar ROLE_USER O ROLE_ADMIN
            //    }
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
        dto.setAdmin("ROLE_USER");

        Usuarios usuario = new Usuarios(dto.getNombre(),dto.getApellidos(), dto.getEmail(),dto.getTelefono(), dto.getContraseña(), dto.getAdmin(),true);
        usuario = usuariosRepository.saveAndFlush(usuario);
        if(usuario == null){
            return new ResponseEntity<>(new Message("No se registro el usuario", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(usuario, "Se registró el usuario", TypesResponse.SUCCESS), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public  ResponseEntity<Object> buscarPorID(UsuarioDTO dto){
        Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(dto.getId());
        if (optionalUsuarios.isPresent()) {
            // Si el usuario existe, devuelve una respuesta exitosa con el usuario
            return new ResponseEntity<>(optionalUsuarios.get(), HttpStatus.OK);
        } else {
            // Si el usuario no existe, devuelve un error
            return new ResponseEntity<>(new Message("Usuario no encontrado", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
    }
    //   - Consultar usuarios

    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(new Message(usuariosRepository.findAll(), "Listado de usuarios", TypesResponse.SUCCESS), HttpStatus.OK);
    }
//- Editar usuarios
@Transactional(rollbackFor = {SQLException.class})
public ResponseEntity<Object> actualizarUsuario(UsuarioDTO dto){
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
//    if(optionalUsuarios.isPresent() && !optionalUsuarios.get().getId().equals(dto.getId())) {
//        return new ResponseEntity<>(new Message("El Correo electronico ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//    }
    dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
    dto.setApellidos(capitalizarPrimeraLetra(dto.getApellidos()));

    Usuarios usuarios = optionalUsuarios.get();
    usuarios.setNombre(dto.getNombre());
    usuarios.setApellidos(dto.getApellidos());
    usuarios.setEmail(dto.getEmail());
    usuarios.setTelefono(dto.getTelefono());
    usuarios.setContraseña(encriptarContraseña(dto.getContraseña())git );


    usuarios = usuariosRepository.saveAndFlush(usuarios);
    if(usuarios == null){
        return new ResponseEntity<>(new Message("No se modificó el suario", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new Message(usuarios, "Se modifico el usuario", TypesResponse.SUCCESS), HttpStatus.OK);
}
//- Cambio de estado de usuarios (habilitar / deshabilitar)
@Transactional(rollbackFor = {SQLException.class})
public ResponseEntity<Object>  cambiarStatus(UsuarioDTO dto){
    Optional<Usuarios> optional = usuariosRepository.findById(dto.getId());
    if(!optional.isPresent()){
        return new ResponseEntity<>(new Message( "No se encontro el usuario", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
    }

    Usuarios usuarios = optional.get();
    usuarios.setStatus(!usuarios.isStatus());
    usuarios = usuariosRepository.saveAndFlush(usuarios);
    if(usuarios == null){
        return new ResponseEntity<>(new Message("No se pudo modificar el estado de el usuario ", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new Message(usuarios, "Se modificó el estado del usuario", TypesResponse.SUCCESS), HttpStatus.OK);
}

//asignar vehiculo a cliente
public ResponseEntity<Object> asignarVehiculo(Long idUsuario, Long idVehiculo) {
    // Buscar el Usuario
    Usuarios usuario = usuariosRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));

    // Buscar el Vehículo
    Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
            .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + idVehiculo));

    // Verificar si el vehículo ya está asignado a otro usuario
    if (vehiculo.getUsuario() != null) {
        Message message = new Message("El vehículo ya está asignado a otro usuario.", TypesResponse.WARNING);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // Asignar el vehículo al usuario
    vehiculo.setUsuario(usuario);

    // Guardar el vehículo actualizado
    vehiculoRepository.save(vehiculo);

    // Agregar el vehículo a la lista de vehículos del usuario (si lo necesitas)
    usuario.getVehiculos().add(vehiculo);
    usuariosRepository.save(usuario);  // Guardar usuario si es necesario

    // Responder con éxito
    Message successMessage = new Message("Vehículo asignado correctamente al usuario.", TypesResponse.SUCCESS);
    return new ResponseEntity<>(successMessage, HttpStatus.OK);
}


//- Iniciar sesión
    //se implementaa con securyyty login
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

