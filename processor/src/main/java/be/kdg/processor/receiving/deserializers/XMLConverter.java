package be.kdg.processor.receiving.deserializers;

import be.kdg.processor.model.CameraMessage;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The XMLConverter will transform the incoming XML message to a CameraMessage object.
 */
@Component
public class XMLConverter implements org.springframework.amqp.support.converter.MessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLConverter.class);

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws org.springframework.amqp.support.converter.MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws org.springframework.amqp.support.converter.MessageConversionException {
        XmlMapper xmlMapper = new XmlMapper();
        CameraMessage cm= null;
        try {
            byte[] bytes = message.getBody();
            String doc2 = new String(bytes, StandardCharsets.UTF_8);
            cm = xmlMapper.readValue(doc2, CameraMessage.class);
        } catch (IOException e) {
            LOGGER.error("Error tijdens het omzetten van Message naar CameraMessage.");
        }
        return cm;
    }
}

