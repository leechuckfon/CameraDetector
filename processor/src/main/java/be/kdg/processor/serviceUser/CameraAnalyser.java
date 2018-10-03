package be.kdg.processor.serviceUser;

import be.kdg.sa.services.CameraServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CameraAnalyser {

    private CameraServiceProxy cameraProxy;

    public CameraAnalyser() {
        this.cameraProxy = new CameraServiceProxy();
    }

    public int AskCamera(int id) {
        try {
            cameraProxy.get(id);
        } catch (IOException e) {

        }
        return 1;
    }
}