package be.kdg.processor.deserializers;

import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class LicensePlateDeserializer extends JsonDeserializer<LicensePlateInfo> {
    @Override
    public LicensePlateInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String plateId = node.get("plateId").textValue();
        String nationalNumber = node.get("nationalNumber").textValue();
        int euroNumber = node.get("euroNumber").intValue();

        return new LicensePlateInfo(plateId,nationalNumber,euroNumber);
    }
}
