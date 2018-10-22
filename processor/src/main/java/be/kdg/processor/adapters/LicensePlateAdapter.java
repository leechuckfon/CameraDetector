package be.kdg.processor.adapters;

import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * De LicensePlateAdapter zal via een nummerplaat de info gerelateerd aan deze nummerplaat opvragen aan de LicensePlateService en deze info teruggeven als een LicensePlateInfo object.
 */
@Component
public class LicensePlateAdapter {

    private LicensePlateServiceProxy lps;

    public LicensePlateAdapter() {
        this.lps = new LicensePlateServiceProxy();
    }

    public LicensePlateInfo askInfo(String plate) throws IOException, LicensePlateNotFoundException {
        LicensePlateInfo lincensePlateInfo;

        try {
            ObjectMapper obj = new ObjectMapper();
            lincensePlateInfo = obj.readValue(lps.get(plate), LicensePlateInfo.class);
        } catch (IOException | LicensePlateNotFoundException e) {
           throw(e);
        }
        return lincensePlateInfo;
    }
}
