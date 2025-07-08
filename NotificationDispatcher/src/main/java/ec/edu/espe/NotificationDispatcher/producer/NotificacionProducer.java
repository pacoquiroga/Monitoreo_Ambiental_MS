package ec.edu.espe.NotificationDispatcher.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarNotificacion(NotificacionDto notificacion) {
        try {
            System.out.println(notificacion);
            String json = objectMapper.writeValueAsString(notificacion);
            rabbitTemplate.convertAndSend("notificationAmbiental.cola", json);
            System.out.println("Enviando notificacion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
