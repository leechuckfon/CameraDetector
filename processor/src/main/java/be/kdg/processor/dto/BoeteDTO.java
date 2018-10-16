package be.kdg.processor.dto;

import be.kdg.processor.model.boete.BOETETYPES;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoeteDTO {
    private BOETETYPES type;
    private int betaling;
    private String overtredingsInfo;
    private int cameraId;
}
