package be.kdg.processor.repos;

import be.kdg.processor.model.Boete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoeteRepo extends JpaRepository<Boete,Long> {
}
