package com.consultoria.notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consultoria.notificaciones.client.AuditoriaFeignClient;
import com.consultoria.notificaciones.model.Auditoria;
import com.consultoria.notificaciones.model.Notificacion;
import com.consultoria.notificaciones.repository.NotificacionRepository;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    @Autowired
    private AuditoriaFeignClient auditoriaFeign;

    public List<Notificacion> listar() {
        return repository.findAll();
    }

    public Notificacion guardar(Notificacion notificacion) {

        Notificacion nueva = repository.save(notificacion);

        Auditoria auditoria = new Auditoria();
        auditoria.setDestinatario(notificacion.getDestinatario());
        auditoria.setCanal(notificacion.getCanal());
        auditoria.setAccion("ENVIO_NOTIFICACION");
        auditoria.setEstado(notificacion.getEstado());
        auditoria.setTimestamp(LocalDateTime.now());

        auditoriaFeign.guardarAuditoria(auditoria);

        return nueva;
    }

    public Notificacion buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
