package be.kdg.processor.receiving.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The DeserializeLocalDateTime class will help deserialize the XML based LocalDateTime from the incoming Message.
 */
public class DeserializeLocalDateTime extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        return LocalDateTime.parse(text);
    }
}
