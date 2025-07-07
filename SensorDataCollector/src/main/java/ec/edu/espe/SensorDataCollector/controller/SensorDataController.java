package ec.edu.espe.SensorDataCollector.controller;

import ec.edu.espe.SensorDataCollector.dto.SensorDataDto;
import ec.edu.espe.SensorDataCollector.model.SensorData;
import ec.edu.espe.SensorDataCollector.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor-readings")
public class SensorDataController {
    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping("/{sensorId}")
    public List<SensorData> getBySensorId(@PathVariable String sensorId) {
        return sensorDataService.getSensorDataBySensorId(sensorId);
    }

    @PostMapping
    public SensorData saveSensorData(@RequestBody SensorDataDto sensorDataDto) {
        return sensorDataService.saveSensorData(sensorDataDto);
    }

}
