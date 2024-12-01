package com.example.GestionDeVehiculos.CategoriasDeServicios.control;
//importaciones

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosRepository;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@Transactional
public class CategoriaDeServiciosService {
    //private final static Logger logger = LoggerFactory.getLogger(CategoriaDeServiciosService.class);
        private final CategoriaDeServiciosRepository repository;

        @Autowired
        public CategoriaDeServiciosService(CategoriaDeServiciosRepository repository){
            this.repository = repository;
        }

    ////////////////////////
    //CONSULTAR CATEGORIAS//
    ////////////////////////
    //transaccion de listar todas las categorias de servicios
    @Transactional(readOnly = true)
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(new Message(repository.findAll(), "Listado de Categorias de Servicios", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    //////////////////////////////
    //CONSULTAR CATEGOIAS ATIVAS//
    /////////////////////////////
    //trnsaccion que lista todas las categorias activas
    @Transactional(readOnly = true)
    public  ResponseEntity<Object> CategoriasActivas(){
        return  new ResponseEntity<>(new Message(repository.findAllByStatusOrderByNombre(true), "Listado de Categorias activas", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////
    //CAMBIAR ESTADO DE CATEGORIA (HABILITAR Y DESHABILITAR)//
    //////////////////////////////////////////////////////////
    //transaccion de cambiar es estado de la categoria
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object>  cambiarStatus(CategoriaDeServiciosDTO dto){
        Optional<CategoriaDeServicios> optional = repository.findById(dto.getId());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message( "No se encontro la categoria", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        CategoriaDeServicios categoriaDeServicios = optional.get();
        categoriaDeServicios.setStatus(!categoriaDeServicios.getStatus());
        categoriaDeServicios = repository.saveAndFlush(categoriaDeServicios);
        if(categoriaDeServicios == null){
            return new ResponseEntity<>(new Message("No se pudo modificar el estado de la categoria ", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(categoriaDeServicios, "Se modificó el estado de la categoria", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    ////////////////////////
    //REGISTRAR CATEGORIA//
    ////////////////////////
    //transaccion para guardar una categoria de servico nueva
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> GuardarCategoria(CategoriaDeServiciosDTO dto){
        //validacion de tamaño para el nombre
    dto.setNombre(dto.getNombre().toUpperCase());
    if(dto.getNombre().length()<3){
        return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
    } else if (dto.getNombre().length()>50) {
        return  new ResponseEntity<>(new Message("El nombre no puede se mayor a 50 caractres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
    }
        //validacion para identificar si el nombre ya existe
    Optional<CategoriaDeServicios> optionalCategoriaDeServicios = repository.searchCategoriaDeServiciosByNombre(dto.getNombre());
    if(optionalCategoriaDeServicios.isPresent()){
        return new ResponseEntity<>(new Message("El nombre de la categoria ya existe",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
    }
    //validacion para el tamaño de lal descricion
    dto.setDescripcion(dto.getDescripcion().toUpperCase());
    if(dto.getDescripcion().length() > 120){
        return new ResponseEntity<>(new Message("La descricion no peude ser mayor a 120 caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
    }else if(dto.getDescripcion().length() <3){
        return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
    }

    //poner primera   mayuscula y las demas minusculas
    dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
    dto.setDescripcion(capitalizarPrimeraLetra(dto.getDescripcion()));

    //inserción
    CategoriaDeServicios categoriaDeServicios = new CategoriaDeServicios(dto.getNombre(), dto.getDescripcion(), true);
    categoriaDeServicios = repository.saveAndFlush(categoriaDeServicios);
    if(categoriaDeServicios == null){
        return new ResponseEntity<>(new Message("No se registro la categoria", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
    }
        return new ResponseEntity<>(new Message(categoriaDeServicios, "Se registró la Categoria", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    ////////////////////////
    //ACTUALIZAR CATEGORIA//
    ////////////////////////
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> actualizarCategoria(CategoriaDeServiciosDTO dto){
        //validar tamaños minimos y maximos del nombre
        dto.setNombre(dto.getNombre().toUpperCase());
        if(dto.getNombre().length()<3){
            return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        } else if (dto.getNombre().length()>50) {
            return  new ResponseEntity<>(new Message("El nombre no puede se mayor a 50 caractres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        //validar minimos y maximos de la descripción
        dto.setDescripcion(dto.getDescripcion().toUpperCase());
        if(dto.getDescripcion().length() > 120){
            return new ResponseEntity<>(new Message("La descricion no peude ser mayor a 120 caracteres",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }else if(dto.getDescripcion().length() <3){
            return  new ResponseEntity<>(new Message("El nombre no puede tener menos de 3 caracteres",TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        //validar si ya existe el nombre
        Optional<CategoriaDeServicios> optionalCategoriaDeServicios = repository.searchCategoriaDeServiciosByNombre(dto.getNombre());
        if(optionalCategoriaDeServicios.isPresent()){
            return new ResponseEntity<>(new Message("El nombre de la categoria ya existe",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }
        //validar si existe esa categoria
        Optional<CategoriaDeServicios> optional = repository.findById(dto.getId());
        if(!optional.isPresent()){
            return new ResponseEntity<>(new Message("No se encontro la categoria",TypesResponse.WARNING),HttpStatus.BAD_REQUEST);
        }

        //poner primera   mayuscula y las demas minusculas
        dto.setNombre(capitalizarPrimeraLetra(dto.getNombre()));
        dto.setDescripcion(capitalizarPrimeraLetra(dto.getDescripcion()));

        CategoriaDeServicios categoriaDeServicios = optional.get();
        categoriaDeServicios.setNombre(dto.getNombre());
        categoriaDeServicios.setDescripcion(dto.getDescripcion());

        categoriaDeServicios = repository.saveAndFlush(categoriaDeServicios);
        if(categoriaDeServicios == null){
            return new ResponseEntity<>(new Message("No se modificó la categoria", TypesResponse.ERROR), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(categoriaDeServicios, "Se modificó la categoria", TypesResponse.SUCCESS), HttpStatus.OK);
    }





    //funciones:

    //funcion poner primerra letra en mayuscula
    public static String capitalizarPrimeraLetra(String texto) {
        // Convierte toda la cadena a min&uacute;sculas.
        texto = texto.toLowerCase();
        // Convierte la primera letra a mayúsculas y la concatena con el resto.
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
