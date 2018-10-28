package be.kdg.processor.model.events;

import org.springframework.context.ApplicationEvent;

public class PropertiesChangeEvent extends ApplicationEvent {
    private String message;

    public PropertiesChangeEvent(Object source) {
        super(source);
        this.message = "Er is een change gebeurd in de application.properties file.";
    }

    public String getMessage() {
        return message;
    }
}
