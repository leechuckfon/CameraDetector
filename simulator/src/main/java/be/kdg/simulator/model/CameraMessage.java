package be.kdg.simulator.model;

import be.kdg.simulator.serializers.SerializeLocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CameraMessage {
    private final int id;
    private final String licensePlate;
    @JsonSerialize(using = SerializeLocalDateTime.class)
    private final LocalDateTime timestamp;
    private final long delay;

    public CameraMessage() {
        id = -9999;
        licensePlate = "X-XXX-XXX";
        timestamp = LocalDateTime.now();
        delay = -1;
    }

    public CameraMessage(int id, String licensePlate, LocalDateTime timestamp) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
        delay = -1;
    }

    public CameraMessage(int id, String licensePlate, LocalDateTime timestamp, long delay) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
        this.delay = delay;
    }

    @Override
    public java.lang.String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
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

    public String getLicensePlate() {
        return licensePlate;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public Long getDelay() {
        return delay;
    }

}
