package be.kdg.processor.web.publishers;

import be.kdg.processor.model.events.PropertiesChangeEvent;
import be.kdg.processor.receiving.configs.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PropertiesChangePublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PropertiesChangePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void doStuffAndPublishAnEvent(final PropertiesConfig propertiesConfig) {
        System.out.println("Publishing custom event. ");
        PropertiesChangeEvent changeEvent = new PropertiesChangeEvent(propertiesConfig);
        applicationEventPublisher.publishEvent(changeEvent);
    }
}
