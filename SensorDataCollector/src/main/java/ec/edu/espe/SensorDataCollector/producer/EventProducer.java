package ec.edu.espe.SensorDataCollector.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.SensorDataCollector.dto.EventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarEvento(EventDto evento) {
        try {
            System.out.println(evento);
            String json = objectMapper.writeValueAsString(evento);
            rabbitTemplate.convertAndSend("event.queue", json);
            System.out.println("Enviando evento");
        } catch (Exception e) {
            e.printStackTrace();
 }
}

}
