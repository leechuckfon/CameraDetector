package be.kdg.processor.model.boete.calculcators;

import be.kdg.processor.model.boete.BoeteTypes;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * De SnelheidBerekening zal een nieuwe (snelheid)Boete aanmaken en deze opslagen in de BoeteRepository aan de hand van de BoeteService.
 */
@Component
public class SnelheidBerekening implements BoeteBerekening {
    private final BoeteService boeteService;

    public SnelheidBerekening(BoeteService boeteService) {
        this.boeteService = boeteService;
    }


    @Override
        public void berekenBoete(int boetefactor, int cameraId,int overtredingsgetal,int maxtoegelatengetal, LocalDateTime overtredingsTijd) {
        /*echte boeteberekening nog te doen*/
            boeteService.saveBoete(new Boete(BoeteTypes.SNELHEID,(overtredingsgetal-maxtoegelatengetal)*boetefactor,cameraId,String.format("Er werd %d gereden op een segment waar het maximum %d was",overtredingsgetal,maxtoegelatengetal),overtredingsTijd));
        }
}