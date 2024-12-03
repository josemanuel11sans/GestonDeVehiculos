package com.example.GestionDeVehiculos;

import com.example.GestionDeVehiculos.CategoriasDeServicios.control.CategoriaDeServiciosService;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosDTO;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServicios;
import com.example.GestionDeVehiculos.CategoriasDeServicios.model.CategoriaDeServiciosRepository;
import com.example.GestionDeVehiculos.Servicios.control.SeviciosService;
import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosDTO;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import com.example.GestionDeVehiculos.Utils.Message;
import com.example.GestionDeVehiculos.Utils.TypesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestionDeVehiculosApplication {

	@Mock
	private CategoriaDeServiciosRepository categoriaDeServiciosRepository;

	@InjectMocks
	private CategoriaDeServiciosService categoriaDeServiciosService;

	@Mock
	private ServiciosRepository repository;

	@InjectMocks
	private SeviciosService seviciosService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Pruebas de CategoriaDeServiciosService
	@Test
	void testGuardarCategoria_ValidaNombreCorto() {
		CategoriaDeServiciosDTO dto = new CategoriaDeServiciosDTO();
		dto.setNombre("Ab");
		dto.setDescripcion("Descripción válida");

		ResponseEntity<Object> response = categoriaDeServiciosService.GuardarCategoria(dto);
		Message message = (Message) response.getBody();
		assertEquals(400, response.getStatusCodeValue());
		assertTrue(message.getText().contains("El nombre no puede tener menos de 3 caracteres"));
	}

	@Test
	void testGuardarCategoria_ValidaNombreLargo() {
		CategoriaDeServiciosDTO dto = new CategoriaDeServiciosDTO();
		dto.setNombre("Nombre demasiado largo para ser aceptado por el sistema");
		dto.setDescripcion("Descripción válida");

		ResponseEntity<Object> response = categoriaDeServiciosService.GuardarCategoria(dto);
		Message message = (Message) response.getBody();

		assertEquals(400, response.getStatusCodeValue());
		assertTrue(message.getText().contains("El nombre no puede se mayor a 50 caractres"),
				"El mensaje esperado no contiene 'El nombre no puede se mayor a 50 caractres'");
	}

	@Test
	void testGuardarCategoria_DescripcionLarga() {
		CategoriaDeServiciosDTO dto = new CategoriaDeServiciosDTO();
		dto.setNombre("Categoria válida");
		dto.setDescripcion("Esta es una descripción muy larga para probar que no se permite una descripción mayor a 120 caracteres. Este texto sigue para asegurarse de que no pase la validación");

		ResponseEntity<Object> response = categoriaDeServiciosService.GuardarCategoria(dto);
		Message message = (Message) response.getBody();

		assertEquals(400, response.getStatusCodeValue());
		assertTrue(message.getText().contains("La descricion no peude ser mayor a 120 caracteres"),
				"El mensaje esperado no contiene 'La descricion no peude ser mayor a 120 caracteres'");
	}

	@Test
	void testGuardarCategoria_CategoriaYaExiste() {
		CategoriaDeServiciosDTO dto = new CategoriaDeServiciosDTO();
		dto.setNombre("Categoria existente");
		dto.setDescripcion("Descripción válida");

		when(categoriaDeServiciosRepository.searchCategoriaDeServiciosByNombre(dto.getNombre()))
				.thenReturn(Optional.of(new CategoriaDeServicios()));

		ResponseEntity<Object> response = categoriaDeServiciosService.GuardarCategoria(dto);
		Message message = (Message) response.getBody();

		assertEquals(400, response.getStatusCodeValue());

		assertTrue(message.getText().contains("No se registro la categoria"),
				"El mensaje esperado no contiene 'No se registro la categoria'. Mensaje recibido: " + message.getText());
	}


	@Test
	void testGuardarCategoria_RegistroExitoso() {
		CategoriaDeServiciosDTO dto = new CategoriaDeServiciosDTO();
		dto.setNombre("Categoria válida");
		dto.setDescripcion("Descripción válida");

		when(categoriaDeServiciosRepository.searchCategoriaDeServiciosByNombre(dto.getNombre()))
				.thenReturn(Optional.empty());
		when(categoriaDeServiciosRepository.saveAndFlush(any(CategoriaDeServicios.class)))
				.thenReturn(new CategoriaDeServicios());

		ResponseEntity<Object> response = categoriaDeServiciosService.GuardarCategoria(dto);
		Message message = (Message) response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(message.getText().contains("Se registró la Categoria"));

		verify(categoriaDeServiciosRepository, times(1)).saveAndFlush(any(CategoriaDeServicios.class));
	}

	// Pruebas de ServiciosService

	@Test
	void testGuardarServicio_ValidaNombreCorto() {
		ServiciosDTO dto = new ServiciosDTO();
		dto.setNombre("Ab");
		dto.setDescripcion("Descripción válida");

		ResponseEntity<Object> response = seviciosService.GuardarServicio(dto);
		Message message = (Message) response.getBody();
		assertEquals(400, response.getStatusCodeValue());
		assertTrue(message.getText().contains("El nombre no puede tener menos de 3 caracteres"));
	}

	@Test
	void testGuardarServicio_RegistroExitoso() {
		ServiciosDTO dto = new ServiciosDTO();
		dto.setNombre("Servicio válido");
		dto.setDescripcion("Descripción válida");

		when(repository.searchServiciosByNombre("Servicio válido")).thenReturn(Optional.empty());
		when(repository.saveAndFlush(any(Servicios.class))).thenReturn(new Servicios());

		ResponseEntity<Object> response = seviciosService.GuardarServicio(dto);
		Message message = (Message) response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(message.getText().contains("Registro exitoso"));

		verify(repository, times(1)).saveAndFlush(any(Servicios.class));
	}

	@Test
	void testConsultarServicio_DevuelveListado() {
		List<Servicios> servicios = new ArrayList<>();
		servicios.add(new Servicios("Servicio1", "Descripción1", null, true));
		servicios.add(new Servicios("Servicio2", "Descripción2", null, true));

		when(repository.findAll()).thenReturn(servicios);

		ResponseEntity<Object> response = seviciosService.ConsultarServicio();
		Message message = (Message) response.getBody();
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(message.getText().contains("Listado de categorias de servicios"));
	}
}
