package be.kdg.processor.receiving.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "receive")
public class PropertiesConfig {
    private long emissionTime;
    private long timeframeSnelheid;
    private long retrydelay;
    private int maxretries;
    private int emissionfinefactor;
    private int speedfinefactor;

    public int getEmissionfinefactor() {
        return emissionfinefactor;
    }

    public void setEmissionfinefactor(int emissionfinefactor) {
        this.emissionfinefactor = emissionfinefactor;
    }

    public int getSpeedfinefactor() {
        return speedfinefactor;
    }

    public void setSpeedfinefactor(int speedfinefactor) {
        this.speedfinefactor = speedfinefactor;
    }

    public long getEmissionTime() {
        return emissionTime;
    }

    public void setEmissionTime(long emissionTime) {
        this.emissionTime = emissionTime;
    }

    public long getTimeframeSnelheid() {
        return timeframeSnelheid;
    }

    public void setTimeframeSnelheid(long timeframeSnelheid) {
        this.timeframeSnelheid = timeframeSnelheid;
    }

    public long getRetrydelay() {
        return retrydelay;
    }

    public void setRetrydelay(long retrydelay) {
        this.retrydelay = retrydelay;
    }

    public int getMaxretries() {
        return maxretries;
    }

    public void setMaxretries(int maxretries) {
        this.maxretries = maxretries;
    }
}
