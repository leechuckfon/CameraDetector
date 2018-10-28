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
    private int emissionfinefactor;
    @NotEmpty
    private int speedfinefactor;
    @NotEmpty
    private long emissionTime;
    @NotEmpty
    private long timeframeSnelheid;
    @NotEmpty
    private long retrydelay;
    @NotEmpty
    private int maxretries;}
