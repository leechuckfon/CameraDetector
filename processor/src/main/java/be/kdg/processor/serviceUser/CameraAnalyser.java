package be.kdg.processor.serviceUser;

import be.kdg.processor.model.Camera;
import be.kdg.processor.model.CameraDeserializer;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CameraAnalyser {

    private CameraServiceProxy cameraProxy;

    public CameraAnalyser() {
        cameraProxy = new CameraServiceProxy();
    }

    public Camera AskInfo(int id) throws IOException, CameraNotFoundException{
        Camera a;
        try {
                ObjectMapper obj = new ObjectMapper();
                a = obj.readValue(cameraProxy.get(id),be.kdg.processor.model.Camera.class);
        } catch (IOException | CameraNotFoundException e) {
            throw(e);
        }
        return a;
    }
}