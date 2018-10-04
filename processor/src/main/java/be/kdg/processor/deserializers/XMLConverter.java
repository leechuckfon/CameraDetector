package be.kdg.processor.deserializers;

import be.kdg.processor.model.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
            String doc2 = new String(bytes, "UTF-8");
            cm = xmlMapper.readValue(doc2, CameraMessage.class);
        } catch (IOException e) {
            LOGGER.error("Error tijdens het omzetten van Message naar CameraMessage.");
        }
        return cm;
    }
}
