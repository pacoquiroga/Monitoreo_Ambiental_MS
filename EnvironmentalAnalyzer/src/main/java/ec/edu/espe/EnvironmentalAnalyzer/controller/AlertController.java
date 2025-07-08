package ec.edu.espe.EnvironmentalAnalyzer.controller;

import ec.edu.espe.EnvironmentalAnalyzer.model.Alert;
import ec.edu.espe.EnvironmentalAnalyzer.service.AlertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alert")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAlerts() {
        return this.alertService.listarAlertas();
    }
}
