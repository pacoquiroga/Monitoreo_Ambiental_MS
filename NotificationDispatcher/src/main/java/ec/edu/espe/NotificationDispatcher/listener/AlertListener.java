package ec.edu.espe.NotificationDispatcher.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.NotificationDispatcher.dto.AlertDto;
import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.model.Notificacion;
import ec.edu.espe.NotificationDispatcher.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertListener {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "alert.cola")
    public void recibirAlerta(String mensajeJson) {
        try {

            NotificacionDto notificacion = mapper.readValue(mensajeJson, NotificacionDto.class);

            notificationService.enviarPush(notificacion);
            notificationService.enviarCorreo(notificacion);

            notificationService.guardarNotificacion(notificacion);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error recibiendo la alerta", e);
        }
    }

}
