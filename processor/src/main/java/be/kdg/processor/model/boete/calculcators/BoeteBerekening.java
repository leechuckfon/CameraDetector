package be.kdg.processor.model.boete.calculcators;

import java.time.LocalDateTime;
/**
 * De interface voor boeteberekeningen (voor elke soort boete moet er een nieuwe berekening zijn)
 */
public interface BoeteBerekening {
    void berekenBoete(int boetefactor, int cameraId, int overtredingsgetal, int maxtoegelatengetal, LocalDateTime overtredingsTijd);
}
