package be.kdg.processor.model.licenseplate;

import be.kdg.processor.deserializers.LicensePlateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = LicensePlateDeserializer.class)
public class LicensePlateInfo {
    private String plateId;
    private String nationalNumber;
    private int euroNumber;

    public LicensePlateInfo(String plateId, String nationalNumber, int euroNumber) {
        this.plateId = plateId;
        this.nationalNumber = nationalNumber;
        this.euroNumber = euroNumber;
    }

    public String getPlateId() {
        return plateId;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public int getEuroNumber() {
        return euroNumber;
    }
}
