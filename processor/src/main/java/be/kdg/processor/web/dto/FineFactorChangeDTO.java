package be.kdg.processor.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FineFactorChangeDTO {
    @NotEmpty
    private int emissionFactor;
    @NotEmpty
    private int speedFactor;
}
