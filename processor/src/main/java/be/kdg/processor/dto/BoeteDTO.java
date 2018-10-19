package be.kdg.processor.dto;

import be.kdg.processor.model.boete.BoeteTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoeteDTO {
    private BoeteTypes type;
    private int betaling;
    private String overtredingsInfo;
    private int cameraId;
    private boolean goedgekeurd;
    private LocalDateTime overtredingsTijd;
    private String motivatie;
}
