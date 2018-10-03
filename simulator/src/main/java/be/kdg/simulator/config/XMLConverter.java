package be.kdg.simulator.config;

import be.kdg.simulator.model.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import java.io.IOException;
@Component
public class XMLConverter implements org.springframework.amqp.support.converter.MessageConverter {

    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties) throws org.springframework.amqp.support.converter.MessageConversionException {
        XmlMapper xm = new XmlMapper();
        String xmlStr = null;
        try {
            xmlStr = xm.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return cm;
    }
}
