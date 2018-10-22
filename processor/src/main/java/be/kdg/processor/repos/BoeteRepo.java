package be.kdg.processor.repos;

import be.kdg.processor.model.boete.Boete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * De Repository waar alle Boete objecten worden opgeslagen.
 */
@Repository
public interface BoeteRepo extends JpaRepository<Boete,Long> {

}