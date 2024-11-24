package com.example.GestionDeVehiculos.Servicios.control;

import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class SeviciosService {
    private final ServiciosRepository repository;

    @Autowired
    public SeviciosService(ServiciosRepository repository) {
        this.repository = repository;
    }

    ////////
    //REGISTRA UN SERVICIO
    ///////

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Object> GuardarServicio(ServiciosDTO  dot){
        //dto.setNombre
    }

}
