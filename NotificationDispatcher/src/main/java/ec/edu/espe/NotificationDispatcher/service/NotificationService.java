package ec.edu.espe.NotificationDispatcher.service;

import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.model.Notificacion;
import ec.edu.espe.NotificationDispatcher.repository.NotificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    @Autowired
    private NotificacionRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void guardarNotificacion(NotificacionDto dto) {
        Notificacion notificacion = new Notificacion();

        notificacion.setMessage(dto.getMensaje());
        notificacion.setEvent_type(dto.getTipo());
        notificacion.setFecha(LocalDateTime.now());
        repository.save(notificacion);
    }

    public void enviarPush(NotificacionDto notificacion) {
        System.out.println("[Push] Notificación push enviada: " + notificacion.getMensaje());
    }

    public void enviarCorreo(NotificacionDto notificacion) {
        logger.info("[Correo] Enviando correo: {}", notificacion.getMensaje());
    }

    public void enviarSms(NotificacionDto notificacion) {
        logger.info("[SMS] Enviando SMS: {}", notificacion.getMensaje());
    }


}

