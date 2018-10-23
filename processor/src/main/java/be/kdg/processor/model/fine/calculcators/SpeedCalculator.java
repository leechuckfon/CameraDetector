package be.kdg.processor.model.fine.calculcators;

import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.services.FineService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The SpeedCalculator will make a new SpeedFine and save this in teh FineRepository using a FineService.
 */
@Component
public class SpeedCalculator implements FineCalculator {
    private final FineService fineService;

    public SpeedCalculator(FineService fineService) {
        this.fineService = fineService;
    }


    @Override
        public void calculateFine(int boetefactor, int cameraId, int overtredingsgetal, int maxtoegelatengetal, LocalDateTime overtredingsTijd) {
        /*echte boeteberekening nog te doen*/
            fineService.saveFine(new Fine(FineType.SPEED,(overtredingsgetal-maxtoegelatengetal)*boetefactor,cameraId,String.format("Er werd %d gereden op een segment waar het maximum %d was",overtredingsgetal,maxtoegelatengetal),overtredingsTijd));
        }
}
