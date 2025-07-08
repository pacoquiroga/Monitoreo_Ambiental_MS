package ec.edu.espe.SensorDataCollector.service;

import ec.edu.espe.SensorDataCollector.dto.EventDto;
import ec.edu.espe.SensorDataCollector.dto.SensorDataDto;
import ec.edu.espe.SensorDataCollector.model.SensorData;
import ec.edu.espe.SensorDataCollector.producer.EventProducer;
import ec.edu.espe.SensorDataCollector.producer.SensorDataProducer;
import ec.edu.espe.SensorDataCollector.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SensorDataService {
    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorDataProducer sensorDataProducer;

    @Autowired
    private EventProducer eventProducer;

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

            eventProducer.enviarEvento(eventDto);
            sensorDataProducer.enviarSensorData(sensorData);

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
