package com.example.GestionDeVehiculos;

import com.example.GestionDeVehiculos.Vehiculos.controller.VehiculosService;
import com.example.GestionDeVehiculos.Vehiculos.model.Vehiculo;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoDTO;
import com.example.GestionDeVehiculos.Vehiculos.model.VehiculoRepository;
import com.example.GestionDeVehiculos.Servicios.model.Servicios;
import com.example.GestionDeVehiculos.Servicios.model.ServiciosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehiculosServiceTest {

	@Mock
	private VehiculoRepository vehiculoRepository;

	@Mock
	private ServiciosRepository serviciosRepository;

	@InjectMocks
	private VehiculosService vehiculosService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Caso de prueba: Registro de Vehículo con Campos Válidos
	// Descripción: Verificar que un vehículo con todos los campos válidos se registre correctamente en el sistema.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testRegistrarVehiculo_CamposValidos() {
		VehiculoDTO dto = new VehiculoDTO();
		dto.setModelo("Civic");
		dto.setMarca("Honda");
		dto.setColor("Rojo");
		dto.setStatus(true);

		Vehiculo vehiculo = new Vehiculo();
		when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

		ResponseEntity<Object> result = vehiculosService.registrarVehiculo(dto);
		assertNotNull(result);
		verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
	}

	// Caso de prueba: Actualización de Vehículo Existente
	// Descripción: Verificar que un vehículo existente pueda ser actualizado correctamente en el sistema.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testActualizarVehiculo_VehiculoExistente() {
		VehiculoDTO dto = new VehiculoDTO();
		dto.setId(1L);
		dto.setModelo("Corolla");
		dto.setMarca("Toyota");
		dto.setColor("Azul");
		dto.setStatus(true);

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(1L);

		when(vehiculoRepository.findById(dto.getId())).thenReturn(Optional.of(vehiculo));
		when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

		ResponseEntity<Object> result = vehiculosService.actualizarVehiculo(dto);
		assertNotNull(result);
		assertEquals("Corolla", dto.getModelo());
		verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
	}

	// Caso de prueba: Consulta de Vehículos Activos
	// Descripción: Verificar que el sistema solo devuelva los vehículos que están activos en el sistema.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testConsultarVehiculosActivos() {
		Vehiculo vehiculo1 = new Vehiculo();
		vehiculo1.setStatus(true);
		Vehiculo vehiculo2 = new Vehiculo();
		vehiculo2.setStatus(false);
		when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculo1, vehiculo2));

		List<Vehiculo> result = vehiculosService.consultarVehiculosActivos();
		assertEquals(1, result.size());
		assertTrue(result.get(0).isStatus());
	}



	// Caso de prueba: Cambio de Estado de un Vehículo No Existente
	// Descripción: Verificar que el sistema muestre un error al intentar cambiar el estado de un vehículo que no está registrado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testAsignarServicio_Exitoso() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setServicios(new HashSet<>());
		Servicios servicio = new Servicios();

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
		when(serviciosRepository.findById(1L)).thenReturn(Optional.of(servicio));

		vehiculosService.asignarServicio(1L, 1L);

		assertTrue(vehiculo.getServicios().contains(servicio));

		verify(vehiculoRepository, times(1)).save(vehiculo);
	}


	// Caso de prueba: Remoción de Servicio No Existente
	// Descripción: Verificar que el sistema muestre un error al intentar remover un servicio que no está registrado en el sistema.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testRemoverServicio_NoExistente() {
		Vehiculo vehiculo = new Vehiculo();
		Servicios servicio = new Servicios();
		vehiculo.setServicios(Set.of(servicio));

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
		when(serviciosRepository.findById(2L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(IllegalArgumentException.class, () -> vehiculosService.removerServicio(1L, 2L));
		assertEquals("Servicio no encontrado con ID: 2", exception.getMessage());
	}

	// Caso de prueba: Consulta de vehículos con filtros específicos
	// Descripción: Verificar que se puedan consultar vehículos activos que coincidan con filtros específicos.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testConsultarVehiculosActivosConFiltro() {
		Vehiculo vehiculo1 = new Vehiculo();
		vehiculo1.setModelo("Civic");
		vehiculo1.setMarca("Honda");
		vehiculo1.setColor("Rojo");
		vehiculo1.setStatus(true);

		Vehiculo vehiculo2 = new Vehiculo();
		vehiculo2.setModelo("Corolla");
		vehiculo2.setMarca("Toyota");
		vehiculo2.setColor("Azul");
		vehiculo2.setStatus(false);

		when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculo1, vehiculo2));

		List<Vehiculo> resultado = vehiculosService.consultarVehiculosActivos();
		assertEquals(1, resultado.size());
		assertEquals("Civic", resultado.get(0).getModelo());
	}

	// Caso de prueba: Asignación de servicio a un vehículo sin servicio
	// Descripción: Verificar que se pueda asignar un servicio a un vehículo que no tiene servicio asignado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testAsignarServicioSinServicio() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(1L);
		vehiculo.setServicios(new HashSet<>());

		Servicios servicio = new Servicios();
		servicio.setId(1L);

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
		when(serviciosRepository.findById(1L)).thenReturn(Optional.of(servicio));

		vehiculosService.asignarServicio(1L, 1L);

		assertTrue(vehiculo.getServicios().contains(servicio));
		verify(vehiculoRepository, times(1)).save(vehiculo);
	}

	// Caso de prueba: Asignación de servicio a un vehículo ya asignado a otro servicio
	// Descripción: Verificar que el sistema permita reasignar un nuevo servicio a un vehículo que ya tiene otro asignado.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testReasignarServicio() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(1L);

		Servicios servicioAnterior = new Servicios();
		servicioAnterior.setId(2L);

		Servicios nuevoServicio = new Servicios();
		nuevoServicio.setId(3L);

		vehiculo.setServicios(new HashSet<>(Set.of(servicioAnterior)));

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
		when(serviciosRepository.findById(3L)).thenReturn(Optional.of(nuevoServicio));

		vehiculosService.asignarServicio(1L, 3L);

		assertTrue(vehiculo.getServicios().contains(nuevoServicio)); // Validar que se agregó
		verify(vehiculoRepository, times(1)).save(vehiculo);
	}

	// Caso de prueba: Asignación de servicio sin seleccionar un servicio
	// Descripción: Verificar que el sistema muestre un error si no se selecciona un servicio para asignar al vehículo.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testAsignarServicioSinSeleccionar() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(1L);

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

		Exception exception = assertThrows(IllegalArgumentException.class, () -> vehiculosService.asignarServicio(1L, null));
		assertEquals("Servicio no encontrado con ID: null", exception.getMessage());
	}

	// Caso de prueba: Remoción de servicio asignado a un vehículo
	// Descripción: Verificar que el sistema permita remover el servicio asignado a un vehículo.
	// Creado por: Antonio Garcia Gonzalez
	@Test
	void testRemoverServicioAsignado() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(1L);
		Servicios servicio = new Servicios();
		servicio.setId(1L);

		vehiculo.setServicios(new HashSet<>(Set.of(servicio)));

		when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
		when(serviciosRepository.findById(1L)).thenReturn(Optional.of(servicio));

		vehiculosService.removerServicio(1L, 1L);

		assertFalse(vehiculo.getServicios().contains(servicio)); // Validar que se eliminó
		verify(vehiculoRepository, times(1)).save(vehiculo);
	}
}
