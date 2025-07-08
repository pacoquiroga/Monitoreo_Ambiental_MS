package ec.edu.espe.NotificationDispatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataDto {
    private String sensorId;
    private String type;
    private Double value;
    private Instant timestamp;
}