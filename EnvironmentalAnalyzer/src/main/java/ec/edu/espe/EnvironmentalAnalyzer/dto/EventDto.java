package ec.edu.espe.EnvironmentalAnalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private String eventId;
    private String sensorId;
    private String type;
    private Double value;
    private Instant timestamp;

}
