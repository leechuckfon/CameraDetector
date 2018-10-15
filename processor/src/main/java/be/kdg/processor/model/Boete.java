package be.kdg.processor.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
    private long id;
    @Column
    private String type;
    @Column
    private int betaling;
    @Column
    private int snelheidEuronorm;
    @Column
    private int maxSnelheidMaxEuronorm;

    public Boete(String type, int snelheidEuronorm, int maxSnelheidMaxEuronorm) {
        this.type = type;
        this.snelheidEuronorm = snelheidEuronorm;
        this.maxSnelheidMaxEuronorm = maxSnelheidMaxEuronorm;
    }


}
