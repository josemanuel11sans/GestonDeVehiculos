package com.example.GestionDeVehiculos.Servicios.control;

import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicios")
public class ServiciosController {
    private final  SeviciosService service;

    @Autowired
    public ServiciosController(SeviciosService service){this.service = service;}
   //guardar un servicio
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Validated(ServiciosDTO.Register.class) @RequestBody ServiciosDTO dto) {
        return service.GuardarServicio(dto);
    }

    @GetMapping("/all")
    public  ResponseEntity<Object> ListarServicios(){
        return  service.ConsultarServicio();
    }


    @GetMapping("/activos")
    public ResponseEntity<Object> listadoActivos(){return service.ServiciosActivos();}

    @PutMapping("/status")
    public ResponseEntity<Object> changeStatus(@Validated(ServiciosDTO.ChangeStatus.class) @RequestBody ServiciosDTO dto){
        return service.cambiarStatus(dto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Object> actualizar(@Validated(ServiciosDTO.Modify.class) @RequestBody ServiciosDTO dto){
        return service.ActualizarServicio(dto);
    }
}
