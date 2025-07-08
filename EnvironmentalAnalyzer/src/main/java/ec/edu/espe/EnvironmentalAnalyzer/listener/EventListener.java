package ec.edu.espe.EnvironmentalAnalyzer.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.EnvironmentalAnalyzer.dto.EventDto;
import ec.edu.espe.EnvironmentalAnalyzer.service.AlertService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
    @Autowired
    private AlertService alertService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "event.cola")
    public void recibirEventos(String mensaje){
        try{
            EventDto sensorDto = objectMapper.readValue(mensaje, EventDto.class);
            alertService.crearAlerta(sensorDto);
            System.out.println(sensorDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
