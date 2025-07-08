package ec.edu.espe.NotificationDispatcher.repository;

import ec.edu.espe.NotificationDispatcher.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}