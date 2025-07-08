package ec.edu.espe.NotificationDispatcher.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.NotificationDispatcher.dto.AlertDto;
import ec.edu.espe.NotificationDispatcher.dto.NotificacionDto;
import ec.edu.espe.NotificationDispatcher.model.Notificacion;
import ec.edu.espe.NotificationDispatcher.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertListener {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "alert.cola")
    public void recibirAlerta(String mensajeJson) {
        try {

            AlertDto alert = mapper.readValue(mensajeJson, AlertDto.class);

            NotificacionDto notificacion = new NotificacionDto();
            notificacion.setEvent_type(alert.getType());


            String mensaje;

            switch (alert.getType()) {
                case "HighTemperatureAlert":
                    mensaje = "¡Alerta! Alta temperatura detectada: "
                            + alert.getValue() + "°C en el sensor "
                            + alert.getSensorId()
                            + ". Umbral: 40°C.";
                    break;
                case "LowHumidityWarning":
                    mensaje = "Advertencia: Humedad baja detectada: "
                            + alert.getValue() + "% en el sensor "
                            + alert.getSensorId()
                            + ". Umbral mínimo: 20%.";
                    break;
                case "SeismicActivityDetected":
                    mensaje = "¡Atención! Actividad sísmica detectada: "
                            + alert.getValue() + " en el sensor "
                            + alert.getSensorId()
                            + ". Umbral: 3.0.";
                    break;
                default:
                    mensaje = "Alerta generada para el sensor "
                            + alert.getSensorId()
                            + ": " + alert.getType();
            }

            notificacion.setMessage(mensaje);
            notificacion.setStatus("CRITICAL");

            notificationService.enviarPush(notificacion);
            notificationService.enviarCorreo(notificacion);

            notificationService.guardarNotificacion(notificacion);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error recibiendo la alerta", e);
        }
    }

}
