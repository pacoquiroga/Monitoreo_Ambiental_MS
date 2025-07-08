package ec.edu.espe.NotificationDispatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDto {
    private String message;
    private String event_type;
    private String status;
}
