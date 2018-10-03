package be.kdg.processor.model;

public class CameraSegment {
    private int connectedCameraId;
    private long distance;
    private int speedLimit;

    public CameraSegment(int connectedCameraId, long distance, int speedLimit) {
        this.connectedCameraId = connectedCameraId;
        this.distance = distance;
        this.speedLimit = speedLimit;
    }

    public int getConnectedCameraId() {
        return connectedCameraId;
    }

    public long getDistance() {
        return distance;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }
}
