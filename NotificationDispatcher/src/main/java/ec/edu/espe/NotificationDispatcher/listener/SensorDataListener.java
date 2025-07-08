package ec.edu.espe.NotificationDispatcher.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.dto.SensorDataDto;
import ec.edu.espe.NotificationDispatcher.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorDataListener {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "sensorData.cola")
    public void recibirSensorData(String mensajeJson) {
        try {
            SensorDataDto sensorDataDto = mapper.readValue(mensajeJson, SensorDataDto.class);

            String mensaje;
            switch (sensorDataDto.getType()) {
                case "temperature":
                    mensaje = "Lectura de temperatura: " + sensorDataDto.getValue() + "°C del sensor " + sensorDataDto.getSensorId();
                    break;
                case "humidity":
                    mensaje = "Lectura de humedad: " + sensorDataDto.getValue() + "% del sensor " + sensorDataDto.getSensorId();
                    break;
                case "seismicActivity":
                    mensaje = "Lectura de actividad sísmica: " + sensorDataDto.getValue() + " del sensor " + sensorDataDto.getSensorId();
                    break;
                default:
                    mensaje = "Lectura de sensor " + sensorDataDto.getSensorId() + ": " + sensorDataDto.getValue();
            }

            NotificacionDto notificacion = new NotificacionDto();
            notificacion.setMessage(mensaje);
            notificacion.setEvent_type(sensorDataDto.getType());
            notificacion.setStatus("INFO");

            notificationService.enviarPush(notificacion);
            notificationService.enviarCorreo(notificacion);
            notificationService.guardarNotificacion(notificacion);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error procesando sensor data", e);
        }
    }

}
