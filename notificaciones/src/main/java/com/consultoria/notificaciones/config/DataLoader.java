package com.consultoria.notificaciones.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consultoria.notificaciones.model.Notificacion;
import com.consultoria.notificaciones.repository.NotificacionRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private NotificacionRepository repository;

    @PostConstruct
    public void cargarDatos() {

        // Evita duplicados
        if (repository.count() > 0) {
            return;
        }

        Notificacion n1 = new Notificacion();
        n1.setCanal("EMAIL");
        n1.setDestinatario("juan@gmail.com");
        n1.setMensaje("Su factura fue enviada correctamente");
        n1.setEstado("ENVIADO");
        n1.setAuditoriaId(1L);

        Notificacion n2 = new Notificacion();
        n2.setCanal("SMS");
        n2.setDestinatario("maria@gmail.com");
        n2.setMensaje("Su reunion fue programada");
        n2.setEstado("ENVIADO");
        n2.setAuditoriaId(2L);

        Notificacion n3 = new Notificacion();
        n3.setCanal("EMAIL");
        n3.setDestinatario("carlos@gmail.com");
        n3.setMensaje("Se creo un ticket de soporte");
        n3.setEstado("ERROR");
        n3.setAuditoriaId(3L);

        Notificacion n4 = new Notificacion();
        n4.setCanal("WHATSAPP");
        n4.setDestinatario("ana@gmail.com");
        n4.setMensaje("Proyecto actualizado correctamente");
        n4.setEstado("ENVIADO");
        n4.setAuditoriaId(4L);

        Notificacion n5 = new Notificacion();
        n5.setCanal("EMAIL");
        n5.setDestinatario("pedro@gmail.com");
        n5.setMensaje("Pago confirmado exitosamente");
        n5.setEstado("ENVIADO");
        n5.setAuditoriaId(5L);

        repository.saveAll(List.of(n1, n2, n3, n4, n5));

        System.out.println("Datos de notificaciones cargados correctamente");
    }
}