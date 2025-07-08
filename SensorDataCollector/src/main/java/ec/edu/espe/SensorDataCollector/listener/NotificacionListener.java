package ec.edu.espe.SensorDataCollector.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.SensorDataCollector.dto.NotificacionDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificacionListener {
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "notificationAmbiental.cola")
    public void recibirEventos(String mensaje){
        try{
            NotificacionDto notificacion = objectMapper.readValue(mensaje, NotificacionDto.class);
            System.out.println("NOTIFICACION" + notificacion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
