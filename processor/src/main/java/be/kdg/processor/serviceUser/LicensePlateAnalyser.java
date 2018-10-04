package be.kdg.processor.serviceUser;

import be.kdg.processor.model.LicensePlateInfo;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LicensePlateAnalyser {

    private LicensePlateServiceProxy lps;

    public LicensePlateAnalyser() {
        this.lps = new LicensePlateServiceProxy();
    }

    public LicensePlateInfo askInfo(String plate) throws IOException, LicensePlateNotFoundException {
        LicensePlateInfo a;

        try {
            ObjectMapper obj = new ObjectMapper();
            a = obj.readValue(lps.get(plate), LicensePlateInfo.class);
        } catch (IOException | LicensePlateNotFoundException e) {
           throw(e);
        }
        return a;
    }
}
