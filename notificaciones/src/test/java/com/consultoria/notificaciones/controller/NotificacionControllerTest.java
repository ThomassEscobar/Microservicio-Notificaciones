package com.consultoria.notificaciones.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.consultoria.notificaciones.model.Notificacion;
import com.consultoria.notificaciones.service.NotificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setCanal("EMAIL");
        notificacion.setDestinatario("juan.perez@consultoria.com");
        notificacion.setMensaje("Notificación de prueba");
        notificacion.setEstado("ENVIADO");
        notificacion.setAuditoriaId(10L);
    }

    // 1. TEST LISTAR
    @Test
    void listar_ok() throws Exception {
        when(service.listar()).thenReturn(List.of(notificacion));

        mockMvc.perform(get("/notificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].canal").value("EMAIL"))
                .andExpect(jsonPath("$[0].destinatario").value("juan.perez@consultoria.com"));

        verify(service, times(1)).listar();
    }

    // 2. TEST BUSCAR POR ID
    @Test
    void buscarPorId_ok() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(notificacion);

        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.canal").value("EMAIL"))
                .andExpect(jsonPath("$.estado").value("ENVIADO"));

        verify(service, times(1)).buscarPorId(1L);
    }

    // 3. TEST GUARDAR
    @Test
    void guardar_ok() throws Exception {
        when(service.guardar(any(Notificacion.class))).thenReturn(notificacion);

        mockMvc.perform(post("/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificacion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("juan.perez@consultoria.com"));

        verify(service, times(1)).guardar(any(Notificacion.class));
    }

    // 4. TEST ELIMINAR
    @Test
    void eliminar_ok() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/notificaciones/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(1L);
    }
}