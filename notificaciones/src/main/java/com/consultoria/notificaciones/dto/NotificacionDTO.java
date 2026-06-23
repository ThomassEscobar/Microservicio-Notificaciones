package com.consultoria.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {

    private String destinatario;

    private String mensajeResumen;

    private String estado;

}
