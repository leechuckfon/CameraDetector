package be.kdg.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoetefactorenDTO {
    private int emissieboetefactor;
    private int snelehidboetefactor;

    public BoetefactorenDTO() {
    }
}
