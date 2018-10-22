package be.kdg.simulator.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * De SerializeLocalDateTime klasse helpt de LocalDateTime van de CameraMessage klasse te serializeren voor XML conversie en het posten naar de RabbitMQ queue.
 */
public class SerializeLocalDateTime extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
