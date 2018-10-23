package be.kdg.processor.model.camera;


import be.kdg.processor.receiving.deserializers.CameraDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@JsonDeserialize(using = CameraDeserializer.class)
public class Camera {
    private int cameraId;
    private Location location;
    private CameraSegment segment;
    private int euroNorm;

    public Camera(int cameraId, Location location, CameraSegment segment) {
        this.cameraId = cameraId;
        this.location = location;
        this.segment = segment;
    }

    public Camera(int cameraId, Location location, CameraSegment segment, int euroNorm) {
        this.cameraId = cameraId;
        this.location = location;
        this.segment = segment;
        this.euroNorm = euroNorm;
    }

    public int getCameraId() {
        return cameraId;
    }

    public Location getLocation() {
        return location;
    }

    public CameraSegment getSegment() {
        return segment;
    }

    public int getEuroNorm() {
        return euroNorm;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraId=" + cameraId +
                ", location=" + location +
                ", segment=" + "test" +
                '}';
    }
}
