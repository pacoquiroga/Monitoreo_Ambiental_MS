package ec.edu.espe.SensorDataCollector.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.SensorDataCollector.dto.EventDto;
import ec.edu.espe.SensorDataCollector.dto.SensorDataDto;
import ec.edu.espe.SensorDataCollector.model.SensorData;
import ec.edu.espe.SensorDataCollector.repository.SensorDataRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SensorDataService {
    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper mapper;

    public SensorData saveSensorData(SensorDataDto sensorDataDto) {
        try{
            SensorData sensorData = new SensorData();
            sensorData.setSensorId(sensorDataDto.getSensorId());
            sensorData.setType(sensorDataDto.getType());
            sensorData.setValue(sensorDataDto.getValue());
            sensorData.setTimestamp(sensorDataDto.getTimestamp());

            String eventId = "EVT-" + UUID.randomUUID().toString();
            EventDto eventDto = new EventDto(
                    eventId,
                    sensorData.getSensorId(),
                    sensorData.getType(),
                    sensorData.getValue(),
                    sensorData.getTimestamp()
            );

            String json = mapper.writeValueAsString(eventDto);
            template.convertAndSend("event.queue", json);

            System.out.println("Evento enviado" + json);

            return sensorDataRepository.save(sensorData);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving sensor data", e);
        }
    }

    public List<SensorData> getSensorDataBySensorId(String sensorId) {
        return sensorDataRepository.findBySensorId(sensorId);
    }
}
