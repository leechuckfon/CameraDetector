package be.kdg.processor.model.fine;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A Fine Object has a id, fineType, fee that has to be paid, cameraId where the fine has been registered
 * the time of when the offense has taken place, extra info about the offense (for a speedFine this would be the max allowed speed and how fast the person was riding at the time of the offense)
 * if the fine has been approved and the motivation if the fee has been changed.
 */
@Data
@Entity
@AllArgsConstructor
public class Fine {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private FineType type;
    @Column
    private int fee;
    @Column
    private int cameraId;
    @Column
    private LocalDateTime offenseTime;
    @Column
    private String offenseInfo;
    @Column
    private boolean approved = false;
    @Column
    private String motivation="Het boetebedrag is niet aangepast.";

    public Fine(FineType type, int fee, int cameraId, String offenseInfo, LocalDateTime offenseTime) {
        this.type = type;
        this.fee = fee;
        this.offenseTime = offenseTime;
        this.cameraId = cameraId;
        this.offenseInfo = offenseInfo;
    }

    public Fine changeFee(int newFee, String motivation) {
        if (!motivation.isEmpty()) {
            this.fee = newFee;
            this.motivation = motivation;
        }
        return this;
    }
}
