package be.kdg.processor.receiving.deserializers;

import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.camera.CameraSegment;
import be.kdg.processor.model.camera.Location;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * The CameraDeserizalizer will make a Camera Object from the JSOn that has been given by the CameraService.
 */
public class CameraDeserializer extends JsonDeserializer<Camera> {
    @Override
    public Camera deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int id;
        Location loc;
        CameraSegment seg = null;
        int euroNorm= Integer.MIN_VALUE;
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
