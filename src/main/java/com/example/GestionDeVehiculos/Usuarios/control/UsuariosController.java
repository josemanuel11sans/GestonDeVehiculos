package com.example.GestionDeVehiculos.Usuarios.control;


import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

   private final UsuariosService service;
   @Autowired
    public UsuariosController(UsuariosService service) {
       this.service = service;
   }
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Validated(UsuarioDTO.Register.class) @RequestBody UsuarioDTO dto) {
        return service.GuardarUsuario(dto);
    }
    @GetMapping("/all")
    public  ResponseEntity<Object> ListarUsuarios(){
        return  service.findAll();
    }
    @PutMapping("/actualizar")
    public ResponseEntity<Object> actualizar(@Validated(UsuarioDTO.Modify.class) @RequestBody UsuarioDTO dto){
        return service.actualizarUsuario(dto);
    }
    @PutMapping("/status")
    public ResponseEntity<Object> changeStatus(@Validated(UsuarioDTO.ChangeStatus.class) @RequestBody UsuarioDTO dto){
        return service.cambiarStatus(dto);
    }
}



