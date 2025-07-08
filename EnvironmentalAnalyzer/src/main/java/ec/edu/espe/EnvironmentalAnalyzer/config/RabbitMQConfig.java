package ec.edu.espe.EnvironmentalAnalyzer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {
    @Bean
    public Queue notificacionesqueue(){
        return QueueBuilder.durable("event.queue").build();
    }
}
