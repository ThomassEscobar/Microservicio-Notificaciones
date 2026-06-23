package com.consultoria.notificaciones.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {

    private Long id;
    @NotBlank
    private String destinatario;
    @NotBlank
    private String canal;
    @NotBlank
    private String accion;
    @NotBlank
    private String estado;
    @NotNull
    private LocalDateTime timestamp;
}