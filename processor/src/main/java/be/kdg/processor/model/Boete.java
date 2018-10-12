package be.kdg.processor.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Boete {
    @Id
    @GeneratedValue
    private int id;
    private String type;
    private int betaling;
    private int snelheicEuronorm;
    private int maxSnelheidMaxEuronorm;
}
