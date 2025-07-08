package ec.edu.espe.NotificationDispatcher.controller;

import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendSms(@RequestBody NotificacionDto notificacion) {
        notificationService.enviarSms(notificacion);
        return "SMS enviado (simulado)";
    }
}
