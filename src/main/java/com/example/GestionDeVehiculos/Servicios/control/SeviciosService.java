package com.example.GestionDeVehiculos.Servicios.control;

import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class SeviciosService {
    private final ServiciosRepository repository;


    @Autowired
    public SeviciosService(ServiciosRepository repository, ServletWebServerFactoryAutoConfiguration servletWebServerFactoryAutoConfiguration) {
        this.repository = repository;
    }



    ///////////////////////////
    //CONSULTAR SERVICIO
    //////////////////////////
    @Transactional(readOnly = true)
    public ResponseEntity<Object> ConsultarServicio(){
        return new ResponseEntity<>(new Message(repository.findAll(), "Listado de categorias de servicios", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    /////////////////
    //consultar servicios activos
    ////////////////
    @Transactional(readOnly = true)
    public  ResponseEntity<Object> ServiciosActivos(){
        return  new ResponseEntity<>(new Message(repository.findAllByStatusOrderByNombre(true), "listado de Servicios activos", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    ////////
    //REGISTRA UN SERVICIO
    ///////
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> GuardarServicio(ServiciosDTO  dot){
        //validacion del nombre
        if(dot.getNombre().length()<3){
            return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }else if(dot.getNombre().length()>50){
            return  new ResponseEntity<>(new Message("El nombre no puede tener mas de 50 caracteres", TypesResponse.WARNING),  HttpStatus.BAD_REQUEST);
        }

        //validacion de caracteres no permitidos
        if (!dot.getNombre().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return new ResponseEntity<>(new Message("El nombre contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (!dot.getDescripcion().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,\\s]+$")) {
            return new ResponseEntity<>(new Message("La descripción contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        Optional<Servicios> optionalServicios = repository.searchServiciosByNombre(dot.getNombre());
        if(optionalServicios.isPresent()){
            return new ResponseEntity<>(new Message("El Nombre del servicio ya existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if(dot.getDescripcion().length()<3){
            return new ResponseEntity<>(new Message("La descripcion no puede ser menor a 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }else if(dot.getDescripcion().length()>100){
            return new ResponseEntity<>(new Message("La descripcion no puede ser mayor a 100 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        dot.setNombre(capitalizarPrimeraLetra(dot.getNombre()));
        dot.setDescripcion(capitalizarPrimeraLetra(dot.getDescripcion()));

        Servicios servicios = new Servicios(dot.getNombre(), dot.getDescripcion(), dot.getCategoria(),true);
        servicios = repository.saveAndFlush(servicios);
        if(servicios == null){
            return new ResponseEntity<>(new Message("No se registro el servicio",TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message("Registro exitoso",TypesResponse.SUCCESS), HttpStatus.OK);

    }


    ////////////
    //CAMBIAR STATUS
    ////////////

    @Transactional(rollbackFor = {SQLException.class})
    public  ResponseEntity<Object> cambiarStatus(ServiciosDTO dot){
        Optional<Servicios> optional = repository.findById(dot.getId());
        if(!optional.isPresent()){
            return  new ResponseEntity<>(new Message("No se encontro la categoria", TypesResponse.SUCCESS), HttpStatus.OK);
        }
        Servicios servicios = optional.get();
        servicios.setStatus(!servicios.isStatus());
        servicios = repository.saveAndFlush(servicios);
        if(servicios == null){
            return  new ResponseEntity<>(new Message("No se pudo modificar el status del servicio", TypesResponse.WARNING) , HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(servicios,"Se modifico el estado del servicio", TypesResponse.SUCCESS),HttpStatus.OK);
    }

    ////////
    //actualizar servicio
    ////////
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Object> ActualizarServicio(ServiciosDTO dto){
//        //validaion de tamaños minimos y maximos
//        if(dto.getNombre().length() < 3){
//            return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }else if(dto.getNombre().length() > 50){
//            return  new ResponseEntity<>(new Message("El nombre no puede tener mas de 50 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }
//
//        //validamos minimos y maximos de la descripcion
//        if(dto.getDescripcion().length() < 3){
//            return  new ResponseEntity<>(new Message("La descripcion no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }else if(dto.getDescripcion().length() > 100){
//            return  new ResponseEntity<>(new Message("La descripcion no puede tener mas de 100 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }
//        //valida si ya existe el nombre
//        Optional<Servicios> optionalServicios = repository.searchServiciosByNombre(dto.getNombre());
//        if(optionalServicios.isPresent()){
//            return new ResponseEntity<>(new Message("El nombre de el servicio ya existe",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
//        }
//        //valida si ya existe el servicio
//        Optional<Servicios> optional = repository.findById(dto.getId());
//        if(!optional.isPresent()){
//            return new ResponseEntity<>(new Message("No se encontro el servicio",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
//        }
//
//        //validacion de caracteres no permitidos
//        if (!dto.getNombre().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
//            return new ResponseEntity<>(new Message("El nombre contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }
//        if (!dto.getDescripcion().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,\\s]+$")) {
//            return new ResponseEntity<>(new Message("La descripción contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
//        }
//        dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
//        dto.setDescripcion(capitalizarPrimeraLetra(dto.getDescripcion()));
//
//        Servicios servicio = optional.get();
//        servicio.setNombre(dto.getNombre());
//        servicio.setDescripcion(dto.getDescripcion());
//        servicio =repository.saveAndFlush(servicio);
//
//        if(servicio == null){
//            return new ResponseEntity<>(new Message("No se modificó ", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(new Message(servicio, "Se modifico el servicio", TypesResponse.SUCCESS), HttpStatus.OK);
//    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> ActualizarServicio(ServiciosDTO dto) {
        // Validación de id
        Optional<Servicios> optional = repository.findById(dto.getId());
        if (!optional.isPresent()) {
            return new ResponseEntity<>(new Message("No se encontró el servicio", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Obtiene el servicio actual desde la base de datos
        Servicios servicio = optional.get();

        // Validación y actualización del nombre si es proporcionado
        if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {
            // Validaciones del nombre
            if (dto.getNombre().length() < 3) {
                return new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            } else if (dto.getNombre().length() > 50) {
                return new ResponseEntity<>(new Message("El nombre no puede tener más de 50 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            // Validación de caracteres no permitidos
            if (!dto.getNombre().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$")) {
                return new ResponseEntity<>(new Message("El nombre contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            // Capitalización de la primera letra
            servicio.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
        }

        // Validación y actualización de la descripción si es proporcionada
        if (dto.getDescripcion() != null && !dto.getDescripcion().isEmpty()) {
            // Validaciones de la descripción
            if (dto.getDescripcion().length() < 3) {
                return new ResponseEntity<>(new Message("La descripción no puede tener menos de 3 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            } else if (dto.getDescripcion().length() > 100) {
                return new ResponseEntity<>(new Message("La descripción no puede tener más de 100 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            // Validación de caracteres no permitidos
            if (!dto.getDescripcion().matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,\\s]+$")) {
                return new ResponseEntity<>(new Message("La descripción contiene caracteres no permitidos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            // Capitalización de la primera letra
            servicio.setDescripcion(capitalizarPrimeraLetra(dto.getDescripcion()));
        }

        // Si se proporciona una categoría, actualizarla (esto es opcional)
        if (dto.getCategoria() != null) {
            servicio.setCategoria(dto.getCategoria());
        }

        // Guardar el servicio actualizado
        servicio = repository.saveAndFlush(servicio);

        if (servicio == null) {
            return new ResponseEntity<>(new Message("No se modificó el servicio", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message(servicio, "Se modificó el servicio", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    //funcion poner primera letra en mayuscula
    public static String capitalizarPrimeraLetra(String texto) {
        // Convierte toda la cadena a min&uacute;sculas.
        texto = texto.toLowerCase();
        // Convierte la primera letra a mayúsculas y la concatena con el resto.
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

}
