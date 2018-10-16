package be.kdg.processor.model.boete.Calculators;

import be.kdg.processor.model.boete.BOETETYPES;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteService;
import org.springframework.stereotype.Component;

@Component
public class EmissieBerekening implements BoeteBerekening {
    private final BoeteService boeteService;

    public EmissieBerekening(BoeteService boeteService) {
        this.boeteService = boeteService;
    }

    @Override
    public void berekenBoete(int boetefactor, int cameraId, String info) {
        boeteService.saveBoete(new Boete(BOETETYPES.EMISSIE,boetefactor,cameraId,info));
    }
}
