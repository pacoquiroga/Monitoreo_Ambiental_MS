package ec.edu.espe.SensorDataCollector.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue eventQueue() {
        return QueueBuilder.durable("event.queue").build();
    }
    @Bean
    public Queue sensorDataQueue() {
        return QueueBuilder.durable("sensorData.queue").build();
    }
}
