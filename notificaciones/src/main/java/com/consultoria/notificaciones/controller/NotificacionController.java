package com.consultoria.notificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.consultoria.notificaciones.model.Notificacion;
import com.consultoria.notificaciones.service.NotificacionService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notificaciones")
@Tag(name = "Notificaciones", description = "Operaciones para el envío, consulta y depuración de alertas y notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @Operation(summary = "Listar todas las notificaciones", description = "Retorna el historial completo de alertas enviadas y registradas en el sistema")
    @GetMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de notificaciones obtenido con éxito"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar notificación por ID", description = "Obtiene los detalles de una alerta específica mediante su identificador único")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Notificacion> buscar(
            @PathVariable 
            @Parameter(description = "ID único de la notificación", required = true, examples = @ExampleObject(value = "1")) Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Crear/Emitir una nueva notificación", description = "Registra y valida una nueva alerta en la bitácora del microservicio")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación creada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de la notificación inválidos o mal estructurados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Notificacion> guardar(
            @Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(notificacion));
    }

    @Operation(summary = "Eliminar una notificación", description = "Elimina permanentemente el registro de una alerta usando su identificador único")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificación eliminada correctamente de los registros"),
        @ApiResponse(responseCode = "404", description = "No se encontró la notificación a eliminar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable 
            @Parameter(description = "ID de la notificación a dar de baja", required = true, examples = @ExampleObject(value = "1")) Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}