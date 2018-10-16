package be.kdg.processor.repos;

import be.kdg.processor.model.boete.Boete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface BoeteRepo extends JpaRepository<Boete,Long> {

}