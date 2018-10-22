package be.kdg.processor.overtredingen;

import be.kdg.processor.model.CameraMessage;

/**
 * Interface voor het maken van Overtredingen (er moet een nieuwe soort Overtreding klasse aangemaakt worden voor elke
 */
public interface Overtreding {
    void handleMessage(CameraMessage m);
}
