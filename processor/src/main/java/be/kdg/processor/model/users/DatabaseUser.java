package be.kdg.processor.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class DatabaseUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String[] roles;

    public DatabaseUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}