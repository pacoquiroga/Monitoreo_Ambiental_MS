package ec.edu.espe.EnvironmentalAnalyzer.config;

import ec.edu.espe.EnvironmentalAnalyzer.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    private AlertService alertService;

    @Scheduled(fixedRate = 10000)
    public void generarReporteDiario(){
        try {
            System.out.println("Generando reporte diario de alertas...");
            System.out.println(alertService.calcularEstadisticasPorTipo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
