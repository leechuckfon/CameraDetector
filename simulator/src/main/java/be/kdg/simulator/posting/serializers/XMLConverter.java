package be.kdg.simulator.posting.serializers;

import be.kdg.simulator.model.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The XML Converter will make sure that the CameraMessage is turned into XML when this is sent to the MessageQueue
 */
@Component
public class XMLConverter implements org.springframework.amqp.support.converter.MessageConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLConverter.class);

    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties) throws org.springframework.amqp.support.converter.MessageConversionException {
        XmlMapper xm = new XmlMapper();
        String xmlStr = null;
        try {
            xmlStr = xm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("CameraMessage kan niet omgezet worden naar XMLString");
        }
        MessageProperties mp = new MessageProperties();
        mp.setContentType(MessageProperties.CONTENT_TYPE_XML);
        return new Message(xmlStr.getBytes(),mp);
    }

    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message) throws org.springframework.amqp.support.converter.MessageConversionException {
        XmlMapper xmlMapper = new XmlMapper();
        CameraMessage cm= null;
        try {
            cm = xmlMapper.readValue(message.toString(), CameraMessage.class);
        } catch (IOException e) {
            LOGGER.error("Message kan niet omgezet worden naar CameraMessage");
        }

        return cm;
    }
}
