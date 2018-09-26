package be.kdg.simulator.model;

import javafx.scene.Camera;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CameraMessage {
    private int id;
    private String licensePlate;
    private LocalDateTime timestamp;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    private int delay = 10;

    public CameraMessage(int i,String s, LocalDateTime now) {
        id = i;
        licensePlate = s;
        timestamp = now;
    }

    public CameraMessage(int id, String licensePlate, LocalDateTime timestamp, int delay) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
        this.delay = delay;
    }

    @Override
    public java.lang.String toString() {
        return String.format("ID: %d, Nummerplaat: %s, Timestamp: %s",getId(),getLicensePlate(),getTimestamp().format(formatter));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CameraMessage that = (CameraMessage) o;
        return id == that.id &&
                Objects.equals(licensePlate, that.licensePlate) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licensePlate, timestamp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
