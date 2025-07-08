package ec.edu.espe.NotificationDispatcher.service;

import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.model.Notificacion;
import ec.edu.espe.NotificationDispatcher.producer.NotificacionProducer;
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

    @Autowired
    private NotificacionProducer producer;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void guardarNotificacion(NotificacionDto dto) {
        Notificacion notificacion = new Notificacion();

        notificacion.setMessage(dto.getMessage());
        notificacion.setEvent_type(dto.getEvent_type());
        notificacion.setStatus(dto.getStatus());
        notificacion.setFecha(LocalDateTime.now());
        repository.save(notificacion);

        producer.enviarNotificacion(notificacion);

    }

    public void enviarPush(NotificacionDto notificacion) {
        System.out.println("[Push] Notificación push enviada: " + notificacion.getMessage());
    }

    public void enviarCorreo(NotificacionDto notificacion) {
        logger.info("[Correo] Enviando correo: {}", notificacion.getMessage());
    }

    public void enviarSms(NotificacionDto notificacion) {
        logger.info("[SMS] Enviando SMS: {}", notificacion.getMessage());
    }


}

