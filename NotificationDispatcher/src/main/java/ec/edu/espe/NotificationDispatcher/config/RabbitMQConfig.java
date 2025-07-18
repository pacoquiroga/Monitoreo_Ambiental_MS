package ec.edu.espe.NotificationDispatcher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue notificacionesAmbienteCola() {
        return QueueBuilder.durable("alert.cola").build();
    }
    @Bean
    public Queue notificacionesCola() {
        return QueueBuilder.durable("notificationAmbiental.cola").build();
    }
    @Bean
    public Queue sensorDataQueue() {
        return QueueBuilder.durable("sensorData.cola").build();
    }

}
