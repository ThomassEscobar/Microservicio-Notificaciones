package com.consultoria.notificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.consultoria.notificaciones.model.Auditoria;

@FeignClient(name = "auditoria-service", url = "http://localhost:8088")
public interface AuditoriaFeignClient {

    @PostMapping("/auditorias")
    Auditoria guardarAuditoria(@RequestBody Auditoria auditoria);
}
