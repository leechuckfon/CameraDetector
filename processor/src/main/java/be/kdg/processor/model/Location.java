package be.kdg.processor.model;

public class Location {
    private float lat;
    private float longitude;

    public Location(float lat, float longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }

    public float getLat() {
        return lat;
    }

    public float getLongitude() {
        return longitude;
    }
}
