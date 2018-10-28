package be.kdg.processor.web.repos;

import be.kdg.processor.model.users.DatabaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<DatabaseUser,Long> {
    DatabaseUser findUserByUsername(String user);
    int deleteByUsername(String username);
}
