package be.kdg.processor.model.boete.Calculators;

import java.time.LocalDateTime;

public interface BoeteBerekening {
    void berekenBoete(int boetefactor, int cameraId, int overtredingsgetal, int maxtoegelatengetal, LocalDateTime overtredingsTijd);
}
