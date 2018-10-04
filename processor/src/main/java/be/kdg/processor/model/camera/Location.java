package be.kdg.processor.model.camera;

public class Location {
    private double lat;
    private double longitude;

    public Location(double lat, double longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public double getLongitude() {
        return longitude;
    }
}
