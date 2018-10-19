package be.kdg.processor.adapters;

import be.kdg.processor.model.camera.Camera;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * De CameraAdapter zal via een id die hij binnenkrijgt, een camera opvragen van de CameraService en deze returnen als een Camera Object.
 */
@Component
public class CameraAdapter {

    private CameraServiceProxy cameraProxy;

    public CameraAdapter() {
        cameraProxy = new CameraServiceProxy();
    }

    public Camera AskInfo(int id) throws IOException, CameraNotFoundException{
        Camera a;
        try {
                ObjectMapper obj = new ObjectMapper();
                a = obj.readValue(cameraProxy.get(id), Camera.class);
        } catch (IOException | CameraNotFoundException e) {
            throw(e);
        }
        return a;
    }
}