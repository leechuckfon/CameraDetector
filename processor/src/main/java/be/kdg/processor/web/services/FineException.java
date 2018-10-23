package be.kdg.processor.web.services;

/**
 * De exception die zal gestuurd worden mocht er een probleem zijn bij het werken met de BoeteService.
 * The exception which will be returned if there has been a problem with the FineService.
 */
public class FineException extends Exception {

    public FineException(String message) {
        super(message);
    }
}
