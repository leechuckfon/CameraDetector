package be.kdg.processor.web.repos;

import be.kdg.processor.model.fine.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Repository where all the Fine Objects will be stored
 */
@Repository
public interface FineRepo extends JpaRepository<Fine,Long> {

}