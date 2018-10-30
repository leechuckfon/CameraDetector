package be.kdg.processor.web.services;

import be.kdg.processor.model.users.DatabaseUser;
import be.kdg.processor.web.repos.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        DatabaseUser databaseUser = userRepo.findUserByUsername(username);
        if (databaseUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.withDefaultPasswordEncoder().username(databaseUser.getUsername()).password(databaseUser.getPassword()).roles(databaseUser.getRoles()).build();
    }

    public UserDetails saveNewUser(DatabaseUser udh) throws UserException {
        if (udh == null) {
            throw new UserException("user was null");
        }
        DatabaseUser databaseUserOUT = userRepo.save(udh);
        return User.withDefaultPasswordEncoder().username(databaseUserOUT.getUsername()).password(databaseUserOUT.getPassword()).roles(databaseUserOUT.getRoles()).build();
    }

    public UserDetails updateUser(DatabaseUser dbu) throws UserException {
        if (dbu == null) {
            throw new UserException("user was null");
        }
        DatabaseUser baseDatabaseUser = userRepo.findUserByUsername(dbu.getUsername());
        baseDatabaseUser.setPassword(dbu.getPassword());
        baseDatabaseUser.setRoles(dbu.getRoles());
        return this.saveNewUser(baseDatabaseUser);
    }

    public int deleteUser(String username) throws UserException {
        if (username.isEmpty()) {
            throw new UserException("user was null");
        }
        return userRepo.deleteByUsername(username);
    }

}
