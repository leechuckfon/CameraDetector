package be.kdg.processor.model.fine.calculcators;

import java.time.LocalDateTime;
/**
 * The interface for FineCalculators (for each kind of fine FineCalculator needs to be implemented
 */
public interface FineCalculator {
    void calculateFine(int finefactor, int cameraId, int offenseNumber, int maxAllowed, LocalDateTime offenseTime,String licensePlate);
}
