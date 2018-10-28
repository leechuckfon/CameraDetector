package be.kdg.processor.model.fine.calculcators;

import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.services.FineService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * EmissionCalculator will make an emissionfine and save this in the FineRepository using the FineService.
 */
@Component
public class EmissionCalculator implements FineCalculator {
    private final FineService fineService;

    public EmissionCalculator(FineService fineService) {
        this.fineService = fineService;
    }

    @Override
    public void calculateFine(int finefactor, int cameraId, int offenseNumber, int maxAllowed, LocalDateTime offenseTime) {


        fineService.saveFine(new Fine(FineType.EMISSION,finefactor,cameraId,String.format("De auto has euronorm %d en het minimum van het segent was %d",offenseNumber,maxAllowed),offenseTime));
    }
}
