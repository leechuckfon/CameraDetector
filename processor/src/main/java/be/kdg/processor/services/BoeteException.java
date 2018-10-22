package be.kdg.processor.services;

/**
 * De exception die zal gestuurd worden mocht er een probleem zijn bij het werken met de BoeteService.
 */
public class BoeteException extends Exception {

    public BoeteException(String message) {
        super(message);
    }
}
