package be.kdg.processor.model;

public class CameraSegment {
    private int connectedCameraId;
    private int distance;
    private int speedLimit;

    public CameraSegment(int connectedCameraId, int distance, int speedLimit) {
        this.connectedCameraId = connectedCameraId;
        this.distance = distance;
        this.speedLimit = speedLimit;
    }

    public int getConnectedCameraId() {
        return connectedCameraId;
    }

    public int getDistance() {
        return distance;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }
}
