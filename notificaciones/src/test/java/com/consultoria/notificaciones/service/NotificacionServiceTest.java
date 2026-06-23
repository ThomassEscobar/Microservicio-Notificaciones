package com.consultoria.notificaciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.consultoria.notificaciones.client.AuditoriaFeignClient;
import com.consultoria.notificaciones.model.Auditoria;
import com.consultoria.notificaciones.model.Notificacion;
import com.consultoria.notificaciones.repository.NotificacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @Mock
    private AuditoriaFeignClient auditoriaFeign;

    @InjectMocks
    private NotificacionService service;

    private Notificacion notificacion;

    @BeforeEach
    public void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("juan.perez@consultoria.com");
        notificacion.setCanal("EMAIL");
        notificacion.setEstado("ENVIADO");
    }

    // 1. TEST LISTAR
    @Test
    public void testListar() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(notificacion));

        // Act
        List<Notificacion> resultado = service.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("juan.perez@consultoria.com", resultado.get(0).getDestinatario());

        verify(repository, times(1)).findAll();
    }

    // 2. TEST GUARDAR (Con llamada Feign de Auditoría)
    @Test
    public void testGuardar() {
        // Arrange
        when(repository.save(any(Notificacion.class))).thenReturn(notificacion);
        // Simulamos que el método void de guardar auditoría no hace nada (comportamiento por defecto)
        doNothing().when(auditoriaFeign).guardarAuditoria(any(Auditoria.class));

        // Act
        Notificacion resultado = service.guardar(notificacion);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("EMAIL", resultado.getCanal());
        assertEquals("ENVIADO", resultado.getEstado());

        verify(repository, times(1)).save(any(Notificacion.class));
        verify(auditoriaFeign, times(1)).guardarAuditoria(any(Auditoria.class));
    }

    // 3. TEST BUSCAR POR ID (Caso Exitoso)
    @Test
    public void testBuscarPorId_Exitoso() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(notificacion));

        // Act
        Notificacion resultado = service.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("juan.perez@consultoria.com", resultado.getDestinatario());

        verify(repository, times(1)).findById(1L);
    }

    // 4. TEST BUSCAR POR ID (Caso Retorna Null)
    @Test
    public void testBuscarPorId_NoEncontrado() {
        // Arrange
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Notificacion resultado = service.buscarPorId(2L);

        // Assert
        assertNull(resultado);
        verify(repository, times(1)).findById(2L);
    }

    // 5. TEST ELIMINAR
    @Test
    public void testEliminar() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        service.eliminar(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }
}