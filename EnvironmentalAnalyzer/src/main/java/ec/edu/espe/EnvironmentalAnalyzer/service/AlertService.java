package ec.edu.espe.EnvironmentalAnalyzer.service;

import ec.edu.espe.EnvironmentalAnalyzer.dto.EventDto;
import ec.edu.espe.EnvironmentalAnalyzer.model.Alert;
import ec.edu.espe.EnvironmentalAnalyzer.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertProducer alertProducer;

    public Alert crearAlerta(EventDto dto) {
        try{
            String type;
            double threshold;
            if(dto.getType() == "temperature" && dto.getValue() > 40){
                type = "HighTemperatureAlert";
                threshold = 40.0;
            } else if(dto.getType() == "humidity" && dto.getValue() < 20) {
                type = "LowHumidityWarning";
                threshold = 20.0;
            } else if(dto.getType() == "seismicActivity" && dto.getValue() > 3.0){
                type = "SeismicActivityDetected";
                threshold = 3.0;
            }
            else {
                // No alert condition met
                return null;
            }
            Alert alert = new Alert();
            alert.setSensorId(dto.getSensorId());
            alert.setValue(dto.getValue());
            alert.setTimestamp(dto.getTimestamp());
            alert.setType(type);
            alert.setThreshold(threshold);

            alertProducer.enviarAlerta(alert);

            return alertRepository.save(alert);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creando la alerta", e);
        }

    }

    public Alert obtenerAlertaPorId(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    public void eliminarAlerta(Long id) {
        alertRepository.deleteById(id);
    }

    public List<Alert> listarAlertas() {
        return alertRepository.findAll();
    }

    public List<Alert> buscarAlertasPorTipo(String tipo) {
        return alertRepository.findByType(tipo);
    }

    public Map<String, Map<String, Double>> calcularEstadisticasPorTipo() {
        List<Alert> todasAlertas = alertRepository.findAll();
        Map<String, Map<String, Double>> estadisticas = new HashMap<>();

        // Get unique types
        List<String> tipos = todasAlertas.stream()
                .map(Alert::getType)
                .distinct()
                .toList();

        for (String tipo : tipos) {
            List<Alert> alertasPorTipo = buscarAlertasPorTipo(tipo);
            List<Double> valores = alertasPorTipo.stream()
                    .map(Alert::getValue)
                    .toList();

            double promedio = valores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double maximo = valores.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
            double minimo = valores.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);

            Map<String, Double> stats = new HashMap<>();
            stats.put("promedio", promedio);
            stats.put("maximo", maximo);
            stats.put("minimo", minimo);

            estadisticas.put(tipo, stats);
        }
        return estadisticas;
    }
}
