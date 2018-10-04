package be.kdg.processor.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

public class CameraDeserializer extends JsonDeserializer<Camera> {
    @Override
    public Camera deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int id;
        Location loc;
        CameraSegment seg = null;
        int euroNorm= Integer.MAX_VALUE;
        JsonNode node = p.getCodec().readTree(p);
        id = (Integer) (node.get("cameraId").numberValue());
        loc = new Location((double)node.get("location").get("lat").numberValue(),(double)node.get("location").get("long").numberValue());
        if (node.get("segment") != null) {
            seg = new CameraSegment((Integer)node.get("segment").get("connectedCameraId").numberValue(),(Integer) node.get("segment").get("distance").numberValue(),(Integer) node.get("segment").get("speedLimit").numberValue());
        }
        if (node.get("euroNorm") != null) {
            euroNorm = (Integer) node.get("euroNorm").numberValue();
        }

        return new Camera(id,loc,seg,euroNorm);
    }
}
