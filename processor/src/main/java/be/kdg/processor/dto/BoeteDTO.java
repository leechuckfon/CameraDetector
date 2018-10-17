package be.kdg.processor.dto;

import be.kdg.processor.deserializers.DeserializeLocalDateTime;
import be.kdg.processor.model.boete.BOETETYPES;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoeteDTO {
    private BOETETYPES type;
    private int betaling;
    private String overtredingsInfo;
    private int cameraId;
    private boolean goedgekeurd;
    private LocalDateTime overtredingsTijd;
    private String motivatie;
}
