package be.kdg.processor.receiving.adapters;

import be.kdg.processor.model.camera.Camera;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The CameraAdapter will transform the JSON acquired by the CameraService into a Camera Object and return this.
 */
@Component
public class CameraAdapter {

    private CameraServiceProxy cameraProxy;

    public CameraAdapter() {
        cameraProxy = new CameraServiceProxy();
    }

    @Cacheable("Cameras")
    public Camera AskInfo(int id) throws IOException, CameraNotFoundException{
        Camera camera=null;
        try {
                ObjectMapper obj = new ObjectMapper();
                camera = obj.readValue(cameraProxy.get(id), Camera.class);
        } catch (IOException | CameraNotFoundException e) {
            throw (e);
        }
        return camera;
    }
}