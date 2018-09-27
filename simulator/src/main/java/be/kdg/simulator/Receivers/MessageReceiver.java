package be.kdg.simulator.Receivers;

import org.springframework.stereotype.Component;

/**
 * Klasse MessageReceiver haalt messages af van de queue
 */

@Component
public class MessageReceiver {

    public void receiveMessage(String message) {
        System.out.println("Received:" +message);
    }

}
