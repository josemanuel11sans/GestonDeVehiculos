package com.example.GestionDeVehiculos.Usuarios.control;

import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


}
