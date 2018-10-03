package be.kdg.processor.model;

public class Camera {
    private int cameraId;
    private Location location;
    private CameraSegment segment;

    public Camera(int cameraId, Location location, CameraSegment segment) {
        this.cameraId = cameraId;
        this.location = location;
        this.segment = segment;
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
}
