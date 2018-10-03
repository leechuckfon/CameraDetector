package be.kdg.processor.converter;

import be.kdg.processor.model.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class XMLConverter implements org.springframework.amqp.support.converter.MessageConverter {

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws org.springframework.amqp.support.converter.MessageConversionException {
//        XmlMapper xm = new XmlMapper();
//        String xmlStr = null;
//        try {
//            xmlStr = xm.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        MessageProperties mp = new MessageProperties();
//        mp.setContentType(MessageProperties.CONTENT_TYPE_XML);
//        return new Message(xmlStr.getBytes(),mp);
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
            e.printStackTrace();
        }
        return cm;
    }
}
