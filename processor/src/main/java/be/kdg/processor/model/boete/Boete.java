package be.kdg.processor.model.boete;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Een Boete Object bestaat uit een id, een BoeteType, een bedrag dat moet betaald worden, de cameraId waardat het wordt geregistreerd
 * de tijd van de overtreding, info over de overtreding (voor een snelheidsovertreding zal die zijn hoe snel bestuurder reed en hoe snel er maximaal gereden mag worden)
 * of de boete is goedgekeurd en de mortivering in geval van het veranderen van het boetebedrag.
 */
@Data
@Entity
@AllArgsConstructor
public class Boete {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private BoeteTypes type;
    @Column
    private int betaling;
    @Column
    private int cameraId;
    @Column
    private LocalDateTime overtredingstijd;
    @Column
    private String overtredingsInfo;
    @Column
    private boolean goedgekeurd = false;
    @Column
    private String motivering="Het boetebedrag is niet aangepast.";

    public Boete(BoeteTypes type, int betaling, int cameraId, String overtredingsInfo, LocalDateTime overtredingstijd) {
        this.type = type;
        this.betaling = betaling;
        this.overtredingstijd = overtredingstijd;
        this.cameraId = cameraId;
        this.overtredingsInfo = overtredingsInfo;
    }

    public Boete pasAanBedrag(int nieuwBedrag, String motivering) {
        if (!motivering.isEmpty()) {
            this.betaling = nieuwBedrag;
        }
        return this;
    }
}
