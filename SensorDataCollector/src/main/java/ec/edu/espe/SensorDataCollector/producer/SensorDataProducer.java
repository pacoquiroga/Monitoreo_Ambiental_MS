package ec.edu.espe.SensorDataCollector.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.SensorDataCollector.model.SensorData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorDataProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarSensorData(SensorData sensorData) {
        try {
            System.out.println(sensorData);
            String json = objectMapper.writeValueAsString(sensorData);
            rabbitTemplate.convertAndSend("sensorData.queue", json);
            System.out.println("Enviando sensorData");
        } catch (Exception e) {
            e.printStackTrace();
}
}

}
