package com.example.GestionDeVehiculos;


import com.example.GestionDeVehiculos.Usuarios.control.UsuariosService;
import com.example.GestionDeVehiculos.Usuarios.model.Usuarios;
import com.example.GestionDeVehiculos.Usuarios.model.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuariosServiceTest {

	@Mock
	private UsuariosRepository usuariosRepository;

	@InjectMocks
	private UsuariosService usuariosService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Caso de prueba: Obtener usuarios por id existente.
	// Descripción: Verifica si el sistema devuelve el usuario correspondiente al id proporcionado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerUsuarioPorId_UsuarioInactivo() {
		Usuarios usuario = new Usuarios();
		usuario.setId(1L);
		usuario.setStatus(false);

		when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

		Usuarios resultado = usuariosService.obtenerUsuarioPorId(1L);

		assertNotNull(resultado);
		assertFalse(resultado.isStatus());
	}


	// Caso de prueba: Obtner usuarios por id nullo.
	// Descripción: Mandar una confirmacion de que el usuario no se encuentra.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerUsuarioPorId_IdNulo() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuariosService.obtenerUsuarioPorId(null);
		});

		assertTrue(exception.getMessage().contains("Usuario no encontrado"));
	}

	// Caso de prueba: Valida que el no sea inactivo.
	// Descripción: Verificar que un vehículo con todos los campos válidos se registre correctamente en el sistema.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void eliminarUsuario_IdNegativo() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuariosService.eliminarUsuario(-1L);
		});

		assertTrue(exception.getMessage().contains("Usuario no encontrado"));
	}

	// Caso de prueba: Verifica que los usuarios no esten vacios.
	// Descripción: Verificar que la lista de usuarios no este vacia.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerTodosLosUsuarios_Vacio() {
		when(usuariosRepository.findAll()).thenReturn(List.of());

		List<Usuarios> resultado = usuariosService.obtenerTodosLosUsuarios();

		assertNotNull(resultado);
		assertTrue(resultado.isEmpty());
	}

	// Caso de prueba: Obtiene todos los datos de los usuarios por medio del id.
	// Descripción: Verificar que los usuarios obtengan los datos de acuerdo a los id de los usuarios.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerTodosLosUsuarios_ConDatos() {
		Usuarios usuario1 = new Usuarios();
		usuario1.setId(1L);
		Usuarios usuario2 = new Usuarios();
		usuario2.setId(2L);

		when(usuariosRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

		List<Usuarios> resultado = usuariosService.obtenerTodosLosUsuarios();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
	}

	// Caso de prueba: Crear un usuario.
	// Descripción: Verificar que los usuarios obtengan los datos de acuerdo a los id de los usuarios.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void crearUsuario_Exito() {
		Usuarios usuario = new Usuarios();
		usuario.setEmail("nuevo.usuario@gmail.com");
		usuario.setTelefono("1234567890");
		usuario.setNombre("Nuevo");
		usuario.setApellidos("Usuario");

		when(usuariosRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
		when(usuariosRepository.existsByTelefono(usuario.getTelefono())).thenReturn(false);
		when(usuariosRepository.save(any(Usuarios.class))).thenAnswer(invocation -> {
			Usuarios u = invocation.getArgument(0);
			u.setId(1L);
			u.setFechaCreacion(Timestamp.from(Instant.now()));
			return u;
		});

		Usuarios resultado = usuariosService.crearUsuario(usuario);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("nuevo.usuario@gmail.com", resultado.getEmail());
		assertEquals("Nuevo", resultado.getNombre());
		verify(usuariosRepository, times(1)).save(usuario);
	}

	// Caso de prueba: Crear usuario con email duplicado.
	// Descripción: Verficar que no se pueda crear un usuario con un email duplicado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void crearUsuario_EmailDuplicado() {
		Usuarios usuario = new Usuarios();
		usuario.setEmail("duplicado@gmail.com");

		when(usuariosRepository.existsByEmail("duplicado@gmail.com")).thenReturn(true);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuariosService.crearUsuario(usuario);
		});

		assertEquals("El correo electrónico ya está registrado.", exception.getMessage());
		verify(usuariosRepository, never()).save(any(Usuarios.class));
	}

	// Caso de prueba: Mofificar un usuario.
	// Descripción: Verficar que se pueda modificar un usuario con exito.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void actualizarUsuario_Exito() {
		Usuarios usuarioExistente = new Usuarios();
		usuarioExistente.setId(1L);
		usuarioExistente.setEmail("original@gmail.com");
		usuarioExistente.setTelefono("1234567890");

		Usuarios datosActualizados = new Usuarios();
		datosActualizados.setEmail("actualizado@gmail.com");
		datosActualizados.setTelefono("0987654321");

		when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
		when(usuariosRepository.existsByEmail("actualizado@gmail.com")).thenReturn(false);
		when(usuariosRepository.existsByTelefono("0987654321")).thenReturn(false);
		when(usuariosRepository.save(any(Usuarios.class))).thenReturn(datosActualizados);

		Usuarios resultado = usuariosService.actualizarUsuario(1L, datosActualizados);

		assertNotNull(resultado);
		assertEquals("actualizado@gmail.com", resultado.getEmail());
		verify(usuariosRepository, times(1)).save(usuarioExistente);
	}

	// Caso de prueba: Eliminar un usuario.
	// Descripción: Verficar que el usuario sea elimindo de manera exitosa.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void eliminarUsuario_Exito() {
		Usuarios usuario = new Usuarios();
		usuario.setId(1L);
		usuario.setStatus(true);

		when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));
		when(usuariosRepository.save(any(Usuarios.class))).thenReturn(usuario);

		boolean resultado = usuariosService.eliminarUsuario(1L);

		assertTrue(resultado);
		assertFalse(usuario.isStatus());
		verify(usuariosRepository, times(1)).save(usuario);
	}

	// Caso de prueba: Obtener usuario por id.
	// Descripción: Verficar que se pueda obtener un usuario por id de maenra exitosa.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerUsuarioPorId_Exito() {
		Usuarios usuario = new Usuarios();
		usuario.setId(1L);

		when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

		Usuarios resultado = usuariosService.obtenerUsuarioPorId(1L);

		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
	}

	// Caso de prueba: Obtener usuario por id no encontrado.
	// Descripción: Verficar que el usuario no sea encontrado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void obtenerUsuarioPorId_NoEncontrado() {
		when(usuariosRepository.findById(1L)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			usuariosService.obtenerUsuarioPorId(1L);
		});

		assertEquals("Usuario no encontrado con el ID: 1", exception.getMessage());
	}
}
