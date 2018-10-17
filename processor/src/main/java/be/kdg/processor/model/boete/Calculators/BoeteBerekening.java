package be.kdg.processor.model.boete.Calculators;

import java.time.LocalDateTime;

public interface BoeteBerekening {
    public void berekenBoete(int boetefactor, int cameraId, String info, LocalDateTime overtredingsTijd);
}
