package com.example.GestionDeVehiculos.Usuarios.control;


import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Usuarios.model.UsuarioDTO;

import com.example.GestionDeVehiculos.Utils.AsignarVehiculoRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/id")
    public ResponseEntity<Object> buscarporid(@RequestBody UsuarioDTO dto){
      return service.buscarPorID(dto);
    }

    // Recibe el JSON con los datos del vehículo y del usuario
    @PostMapping("/asignarVehiculo")
    public ResponseEntity<Object> asignarVehiculo(@RequestBody AsignarVehiculoRequest request) {
        return service.asignarVehiculo(request.getIdUsuario(), request.getIdVehiculo());
    }
}



