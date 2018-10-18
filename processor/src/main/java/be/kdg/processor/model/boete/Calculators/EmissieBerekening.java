package be.kdg.processor.model.boete.Calculators;

import be.kdg.processor.model.boete.BOETETYPES;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmissieBerekening implements BoeteBerekening {
    private final BoeteService boeteService;

    public EmissieBerekening(BoeteService boeteService) {
        this.boeteService = boeteService;
    }

    @Override
    public void berekenBoete(int boetefactor, int cameraId,int overtredingsgetal,int maxtoegelatengetal, LocalDateTime overtredingsTijd) {
        boeteService.saveBoete(new Boete(BOETETYPES.EMISSIE,boetefactor,cameraId,String.format("De auto has euronorm %d en het minimum van het segent was %d",overtredingsgetal,maxtoegelatengetal),overtredingsTijd));
    }
}
