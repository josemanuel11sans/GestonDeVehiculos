package com.example.GestionDeVehiculos.CategoriasDeServicios.control;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CategoriasDeServicios")
//agregra el Croos si se require para consumoQ
public class CategoriaDeServiciosController {
    private final CategoriaDeServiciosService service;

    @Autowired
    public CategoriaDeServiciosController(CategoriaDeServiciosService service) {
        this.service = service;
    }
    //listar todas las categorías
    @GetMapping("/all")
    public ResponseEntity<Object> listarCategoriasDeServicios() {
        return service.findAll();
    }

    //Listas categorias activas
    @GetMapping("/activos")
    public ResponseEntity<Object> listarActivos() {
        return service.CategoriasActivas();
    }

    //guardar una nueva categoría de servicio
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Validated(CategoriaDeServiciosDTO.Register.class) @RequestBody CategoriaDeServiciosDTO dto) {
        return service.GuardarCategoria(dto);
    }

    //actualixar categora de servicios
    @PutMapping("/actualizar")
    public ResponseEntity<Object> actualizar(@Validated(CategoriaDeServiciosDTO.Modify.class) @RequestBody CategoriaDeServiciosDTO dto){
        return service.actualizarCategoria(dto);
    }

    //cambiar es status de la categoria
    @PutMapping("/status")
    public ResponseEntity<Object> changeStatus(@Validated(CategoriaDeServiciosDTO.ChangeStatus.class) @RequestBody CategoriaDeServiciosDTO dto) {
        return service.cambiarStatus(dto);
    }
}
