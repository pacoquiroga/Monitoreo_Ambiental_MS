package ec.edu.espe.EnvironmentalAnalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.EnvironmentalAnalyzer.model.Alert;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarAlerta(Alert alerta) {
        try {
            System.out.println(alerta);
            String json = objectMapper.writeValueAsString(alerta);
            rabbitTemplate.convertAndSend("alert.cola", json);
            System.out.println("Enviando alerta");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}