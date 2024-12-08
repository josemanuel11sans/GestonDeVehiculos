package com.example.GestionDeVehiculos.security;
/*
* Este código define una implementación personalizada de la interfaz UserDetailsService de Spring Security, llamada UserDetailsServiceImpl.
* El propósito de esta clase es cargar los detalles de un usuario desde una base de datos para poder usarlos en el proceso de autenticación y autorización.
* */
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Anotación @Service: Marca la clase como un servicio gestionado por Spring, lo que significa que
// Spring se encargará de la creación de instancias y la inyección de dependencias.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /*
    * Se inyecta el UserRepository en el constructor, lo que permite interactuar con
    * la base de datos para buscar un usuario por su nombre de usuario.
    * UserRepository es un repositorio que probablemente extiende JpaRepository,
    *  lo que facilita las operaciones de base de datos como findByUsername.
    * */
    private final UsuariosRepository usuariosRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuarios user = usuariosRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getContraseña(),
                List.of(new SimpleGrantedAuthority(user.getAdmin()))
        );
    }
}

//Este código implementa la interfaz UserDetailsService de Spring Security para cargar la información
// del usuario desde la base de datos, usando UserRepository.
//Al autenticarse un usuario, el sistema buscará su nombre de usuario en la base de datos.
// Si no lo encuentra, lanzará una excepción UsernameNotFoundException.
//Si el usuario es encontrado, se crea un objeto UserDetails con el nombre de usuario,
// la contraseña y los roles del usuario, que es lo que Spring Security usará para procesar la autenticación.