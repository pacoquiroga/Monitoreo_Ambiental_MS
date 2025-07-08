package ec.edu.espe.EnvironmentalAnalyzer.repository;

import ec.edu.espe.EnvironmentalAnalyzer.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByType(String type);
}
