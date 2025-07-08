package ec.edu.espe.SensorDataCollector.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.EnvironmentalAnalyzer.dto.NotificacionDto;
import ec.edu.espe.EnvironmentalAnalyzer.service.AlertService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "notificationAmbiental.cola")
    public void recibirEventos(String mensaje){
        try{
            NotificacionDto notificacion = objectMapper.readValue(mensaje, NotificacionDto.class);
            System.out.println(notificacion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
