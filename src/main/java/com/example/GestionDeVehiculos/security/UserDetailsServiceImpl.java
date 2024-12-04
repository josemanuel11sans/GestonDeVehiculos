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
        //buscar un usuario en la base de datos
        //Este código intenta obtener un objeto User desde la base de datos utilizando el username.
        //Si el usuario no se encuentra, lanza una excepción UsernameNotFoundException, la cual es
        // una excepción estándar en Spring Security cuando no se puede encontrar un usuario con el
        // nombre de usuario proporcionado.
        Usuarios user = usuariosRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        //Crear un UserDetails de Spring Security:
        //El siguiente paso es crear un objeto de tipo UserDetails, que es lo que Spring Security usa para manejar la autenticación y autorización.
        //user.getUsername() obtiene el nombre de usuario del objeto User recuperado de la base de datos.
        //user.getPassword() obtiene la contraseña del usuario.
        //user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())) mapea los roles del usuario a objetos
        // de tipo SimpleGrantedAuthority. Spring Security usa estos roles para controlar los permisos del usuario.
        //collect(Collectors.toList()) convierte el flujo de autoridades (roles) en una lista, que es el formato que Spring Security espera.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getContraseña(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));


        //Finalmente, se retorna un objeto de tipo User de Spring Security (no confundir con el objeto
        // User de tu modelo), el cual contiene la información necesaria para la autenticación:
        // nombre de usuario, contraseña y los roles del usuario.
    }
}

//Este código implementa la interfaz UserDetailsService de Spring Security para cargar la información
// del usuario desde la base de datos, usando UserRepository.
//Al autenticarse un usuario, el sistema buscará su nombre de usuario en la base de datos.
// Si no lo encuentra, lanzará una excepción UsernameNotFoundException.
//Si el usuario es encontrado, se crea un objeto UserDetails con el nombre de usuario,
// la contraseña y los roles del usuario, que es lo que Spring Security usará para procesar la autenticación.