package com.glovo.app.Scheduler;

import com.glovo.app.services.SoporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoporteScheduler {

    private final SoporteService soporteService;


@Scheduled(fixedRate = 60 * 60 * 1000)
    public void ejecutarArchivado() {
        soporteService.archivarMensajesAntiguos();
        System.out.println("✔ Archivos de soporte revisados automáticamente");
    }
}
