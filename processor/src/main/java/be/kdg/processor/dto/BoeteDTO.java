package be.kdg.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoeteDTO {
    private String type;
    private int betaling;
    private int snelheidEuronorm;
    private int maxSnelheidMaxEuronorm;
}
