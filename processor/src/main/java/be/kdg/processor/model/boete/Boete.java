package be.kdg.processor.model.boete;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
public class Boete {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private BOETETYPES type;
    @Column
    private int betaling;
    @Column
    private int cameraId;
    @Column
    private String overtredingsInfo;

    public Boete(BOETETYPES type, int betaling, int cameraId, String overtredingsInfo) {
        this.type = type;
        this.betaling = betaling;
        this.cameraId = cameraId;
        this.overtredingsInfo = overtredingsInfo;
    }
}
